package com.kattanweb.android.shut;

import android.app.Activity;
import android.os.Bundle;

public class NoSuperUser extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_super_user);
    }

}
