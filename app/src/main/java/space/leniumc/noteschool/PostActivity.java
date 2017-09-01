package space.leniumc.noteschool;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.like.LikeButton;

import java.io.Serializable;

import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;

public class PostActivity extends AppCompatActivity {

    public AvatarView avatarView;
    public TextView nameTextView, gradeTextView, descriptionTextView, attachmentCountTextView;
    public String[] attachmentUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Intent intent = getIntent();
        PostData postData = intent.getExtras().getParcelable("everything");

        avatarView = (AvatarView) findViewById(R.id.avatar_view_post);
        nameTextView = (TextView) findViewById(R.id.name_text_view);
        gradeTextView = (TextView) findViewById(R.id.grade_text_view);
        descriptionTextView = (TextView) findViewById(R.id.description_text_view);
        attachmentCountTextView = (TextView) findViewById(R.id.attachment_count_text_view);
        PicassoLoader imageLoader = new PicassoLoader();

        if (postData != null) {
            imageLoader.loadImage(avatarView, postData.getAvatarImageUrl(),
                    postData.getUserName());
            nameTextView.setText(postData.getUserName());
            gradeTextView.setText(postData.getUserGrade());
            descriptionTextView.setText(postData.getPostDescription());
            attachmentCountTextView.setText(String.valueOf(postData.getAttachmentCount()));
            attachmentUrls = postData.getAttachmentUrls();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.download) {
            new AlertDialog.Builder(this)
                    .setTitle("下载")
                    .setMessage("下载该分享所有附件？")
                    .setIcon(R.drawable.ic_file_download_black_24dp)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            for (String attachmentUrl : attachmentUrls) {
                                // TODO: download files in url
                            }
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        }
        return true;
    }

    public void exitActivity(View view) {
        finish();
    }

}
