package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("Up77awo6XKEz8c0NdPQw3P9RA6kmfKyx0tNs3vuj")
                // if defined
                .clientKey("nAlf1TJrhND0LxboL7Rq05zH2DPEMySqdPEi3uL7")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
