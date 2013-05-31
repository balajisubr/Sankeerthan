package com.bhajans;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.layout);
             
        new Handler().postDelayed(new Runnable(){
            public void run() {
                Intent mainIntent = new Intent(MainActivity.this,MainActivity1.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        }, 2000);
    }
}