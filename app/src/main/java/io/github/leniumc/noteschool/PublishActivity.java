package io.github.leniumc.noteschool;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PublishActivity extends AppCompatActivity {

    private List<AttachmentData> dataList;
    private LinearLayoutManager linearLayoutManager;
    private AttachmentAdapter adapter;
    private static final int READ_REQUEST_CODE = 42;
    private static final int ATTACHMENT_MAX_COUNT = 9;
    private Button attachmentButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_publish);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        attachmentButton = (Button) findViewById(R.id.attachment_button);

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
            EditText titleEditText = (EditText) findViewById(R.id.title_edit_text);
            EditText descriptionEditText = (EditText) findViewById(R.id.description_edit_text);
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();

            // publish post
        }
        return super.onOptionsItemSelected(item);
    }

    public void exitActivity(View view) {
        finish();
    }

    public void publishPost(String title, String description) {

    }

    public void addAttachment(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                String str = uri.getPath();
                File file = new File(str);
                if (file.exists()) {
                    Log.i("tag", "onActivityResult: file exists");
                }
                dataList.add(new AttachmentData(file.getName(), file.length()));
            }
        }

        if (dataList.size() >= ATTACHMENT_MAX_COUNT) {
            attachmentButton.setVisibility(View.GONE);
        }

        adapter.notifyDataSetChanged();
    }
}
