package com.example.john.flickrbrowserapp;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by John on 10/3/2016.
 */
public class BaseActivity  extends AppCompatActivity{
    private Toolbar mToolbar;

    protected Toolbar activateToolbar() {
        if(mToolbar == null) {
            mToolbar = (Toolbar)
                    findViewById(R.id.app_bar);
            if(mToolbar != null) {
                setSupportActionBar(mToolbar);
            }
        }
        return mToolbar;
    }
}
