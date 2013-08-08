package com.sankeerthan;


import com.sankeerthan.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.layout);
             
        new Handler().postDelayed(new Runnable(){
            public void run() {
                Intent mainIntent = new Intent(MainActivity.this,MainActivity1.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        }, 3500);
    }
}