package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UsersPosts extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_posts);

        linearLayout = findViewById(R.id.linearLayout);

        Intent receivedUsername = getIntent();
        String username = receivedUsername.getStringExtra("username");

        ParseQuery<ParseObject> parseQuery= new ParseQuery("photo");
        parseQuery.whereEqualTo("username",username);
        parseQuery.orderByDescending("createdAt");

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects.size() > 0 && e == null) {

                    for(ParseObject posts : objects) {

                        TextView postDescription = new TextView(UsersPosts.this);
                        postDescription.setText(posts.get("image_des") +"");

                        ParseFile postPicture = (ParseFile) posts.get("picture");

                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {

                                if(data != null && e == null) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView postimageView = new ImageView(UsersPosts.this);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(5,5,5,5);
                                    postimageView.setLayoutParams(params);
                                    postimageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postimageView.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams desc_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                    desc_params.setMargins(5,5,5,5);
                                    postDescription.setLayoutParams(desc_params);
                                    postDescription.setGravity(Gravity.CENTER);
                                    postDescription.setBackgroundColor(Color.BLUE);
                                    postDescription.setTextColor(Color.WHITE);
                                    postDescription.setTextSize(30f);

                                    linearLayout.addView(postimageView);
                                    linearLayout.addView(postDescription);

                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(UsersPosts.this, "No posts :(", Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();
            }
        });
    }
}