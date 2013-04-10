package com.bhajans;

import java.util.ArrayList;

import com.bhajans.lookup.LookUpInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;

public class MainActivity extends Activity {


    @Override
    public void onCreate(Bundle icicle) {

        super.onCreate(icicle);
        setContentView(R.layout.layout);
             
        new Handler().postDelayed(new Runnable(){
            public void run() {
                Intent mainIntent = new Intent(MainActivity.this,MainActivity2.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        }, 2000);
    }
}