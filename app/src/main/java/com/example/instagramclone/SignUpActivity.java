package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    EditText email;
    EditText username;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        setTitle("Sign Up");

        email = findViewById(R.id.edtSignupEmail);
        username = findViewById(R.id.edtSignupUsername);
        password = findViewById(R.id.edtSignupPassword);

        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    signUp();
                }
                return false;
            }
        });

        if(ParseUser.getCurrentUser() != null) {
            //ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }


        findViewById(R.id.loginTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LogInActivity.class);
                startActivity(intent);
            }
        });

        signUp();

        }

        public void signUp() {
            findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    rootLayoutTapped(v);

                    if(email.getText().toString().equals("")) {
                        Toast.makeText(SignUpActivity.this, "Email Id is required !!", Toast.LENGTH_SHORT).show();
                    }
                    else if(username.getText().toString().equals("")) {
                        Toast.makeText(SignUpActivity.this, "Username is required !!" , Toast.LENGTH_SHORT).show();
                    }
                    else if(password.getText().toString().equals("")) {
                        Toast.makeText(SignUpActivity.this, "Password is required !!" , Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        ParseUser appUser = new ParseUser();
                        appUser.setEmail(email.getText().toString());
                        appUser.setUsername(username.getText().toString());
                        appUser.setPassword(password.getText().toString());

                        ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
                        progressDialog.setMessage("Signing up " + username.getText().toString());
                        progressDialog.show();

                        appUser.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {

                                if (e == null) {
                                    transitionToSocialMediaActivity();
                                    Toast.makeText(SignUpActivity.this, "Successfully Sign up", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(SignUpActivity.this, "Sign up failed Try Again !!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.i("error", e.getMessage());
                                }
                                progressDialog.dismiss();
                            }
                        });
                    }
                }
            });
        }

        public void rootLayoutTapped(View view) {

        try {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        } catch(Exception e) {
            e.printStackTrace();
        }

        }

        public void transitionToSocialMediaActivity() {

        Intent intent = new Intent(SignUpActivity.this,SocialMediaActivity.class);
        startActivity(intent);
        finish();
        }
}