package io.github.leniumc.noteschool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignInActivity extends AppCompatActivity {

    private static final String SERVER_IP = "http://192.168.0.105/NoteSchool/";

    private EditText studentIdEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        studentIdEditText = findViewById(R.id.student_id);
        passwordEditText = findViewById(R.id.password);
    }

    public void login(View view) {
        String studentId = studentIdEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        byte[] bytesOfMessage = new byte[0];
        try {
            bytesOfMessage = password.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] digest = md.digest(bytesOfMessage);
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        password = sb.toString();
        new PostRequestTask().execute(studentId, password);
    }

    private class PostRequestTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {
            final OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("user_id", params[0])
                    .add("user_password", params[1])
                    .build();
            Request request = new Request.Builder()
                    .url(SERVER_IP + "login_user.php")
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                Log.d("tag", response.body().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return params;
        }

        @Override
        protected void onPostExecute(String... params) {
            SharedPreferences preferences = getSharedPreferences("login_credentials", 0);
            preferences.edit()
                    .putString("user_id", params[0])
                    .putString("password", params[1])
                    .apply();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            getApplicationContext().startActivity(intent);
        }
    }

    public void startRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        this.startActivity(intent);
    }
}
