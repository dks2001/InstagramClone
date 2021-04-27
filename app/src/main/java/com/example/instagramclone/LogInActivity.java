package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LogInActivity extends AppCompatActivity {

    EditText loginusername;
    EditText loginpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        loginusername = findViewById(R.id.edtLoginUsername);
        loginpassword = findViewById(R.id.edtLoginPassword);

        //if (ParseUser.getCurrentUser() != null) {
          //  ParseUser.getCurrentUser().logOut();
        //}

        findViewById(R.id.signupTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rootLayoutTappedLogin(v);

                if(loginusername.getText().toString().equals("")) {
                    Toast.makeText(LogInActivity.this, "Username is required !!", Toast.LENGTH_SHORT).show();
                }
                else if(loginpassword.getText().toString().equals("")) {
                    Toast.makeText(LogInActivity.this, "Password is required !!" , Toast.LENGTH_SHORT).show();
                }

                ParseUser.logInInBackground(loginusername.getText().toString(), loginpassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null) {
                            transitionToSocialMediaActivity();
                            Toast.makeText(LogInActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(LogInActivity.this, "login failed Try Again !!", Toast.LENGTH_SHORT).show();
                            Log.i("error", e.getMessage());
                        }
                    }
                });
            }
        });

    }

    public  void rootLayoutTappedLogin(View view) {
        try {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void transitionToSocialMediaActivity() {

        Intent intent = new Intent(LogInActivity.this,SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }
}