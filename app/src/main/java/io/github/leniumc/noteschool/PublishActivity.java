package io.github.leniumc.noteschool;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PublishActivity extends AppCompatActivity {

    private static final String SERVER_IP = "http://10.8.1.248/NoteSchool/";

    private List<AttachmentData> dataList;
    private LinearLayoutManager linearLayoutManager;
    private AttachmentAdapter adapter;
    private static final int READ_REQUEST_CODE = 42;
    private static final int ATTACHMENT_MAX_COUNT = 9;
    private Button attachmentButton;
    private List<String> filePaths = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_publish);
        Toolbar toolbar = findViewById(R.id.toolbar);
        attachmentButton = findViewById(R.id.attachment_button);

        if (dataList == null) {
            dataList = new ArrayList<>();
        }

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new AttachmentAdapter(this, dataList);
        recyclerView.setAdapter(adapter);

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu_publish, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.send) {
            EditText titleEditText = findViewById(R.id.title_edit_text);
            EditText descriptionEditText = findViewById(R.id.description_edit_text);
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();

            publishPost(title, description, this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void exitActivity(View view) {
        finish();
    }

    public void publishPost(String title, String description, Context context) {
        try {
            MultipartUploadRequest request =
                    new MultipartUploadRequest(context, SERVER_IP + "create_post.php")
                            .setUtf8Charset()
                    // starting from 3.1+, you can also use content:// URI string instead of absolute file
                            .setMaxRetries(2)
                            .addParameter("post_user_id", "13010303")
                            .addParameter("post_user_password", "neil20001110")
                            // TODO: id and password here
                            .addParameter("post_title", title)
                            .addParameter("post_description", description)
                            .addParameter("post_user_name", "neil");
            // starting from 3.1+, you can also use content:// URI string instead of absolute file
            for (String path: filePaths) {
                request.addFileToUpload(path, "uploaded_files[]")
                        .setNotificationConfig(new UploadNotificationConfig()
                                .setTitleForAllStatuses(new File(path).getName()));
            }
            String uploadId = request.startUpload();

        } catch (Exception exc) {
            Log.e("AndroidUploadService", exc.getMessage(), exc);
        }
    }

    public void addAttachment(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = resultData.getData();
            String path = getPath(this, uri);
            filePaths.add(path);
            if (path != null) {
                File file = new File(path);
                dataList.add(new AttachmentData(file.getName(), convertFileSizeUnit(file.length())));
            } else {
                new AlertDialog.Builder(this).setTitle("文件错误")
                        .setMessage("文件选择有误，请尝试更换文件或文件目录")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {}
                        });
            }

        }

        if (dataList.size() >= ATTACHMENT_MAX_COUNT) {
            attachmentButton.setVisibility(View.GONE);
        }

        adapter.notifyDataSetChanged();
    }


    public String convertFileSizeUnit(long fileLength) {
       if (fileLength >= 1e6) {
           return new DecimalFormat("#.##").format(1.0 * fileLength / 1e6) + "MB";
       } else if (fileLength >= 1e3) {
           return new DecimalFormat("#.##").format(1.0 * fileLength / 1e3) + "KB";
       } else {
           return new DecimalFormat("#.##").format(1.0 * fileLength) + "B";
       }
    }


    /*
      All code below are from the open source library aFileChooser by Paul Burke.
      https://stackoverflow.com/questions/19834842/android-gallery-on-android-4-4-kitkat-returns-different-uri-for-intent-action
     */

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @author paulburke
     */
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public void uploadMultipart(final Context context, final String filePath, final int postId) {

    }

}
