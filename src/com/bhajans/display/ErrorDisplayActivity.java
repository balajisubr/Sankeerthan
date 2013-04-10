package com.bhajans.display;

import com.bhajans.R;
import com.bhajans.model.Bhajan;
import com.bhajans.playback.PlaybackActivity;
import com.bhajans.search.SearchBhajan;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
 
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.widget.EditText;

public class ErrorDisplayActivity extends Activity{
	String[] errorMessages = new String[5];
	{
	for ( int i = 0; i< 5; i++){
        errorMessages[i] = "";
}
	}
	
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("INSIDE ERROR ACTIVITY");
	  super.onCreate(savedInstanceState);
	  this.setContentView(R.layout.error_msg);
	  errorMessages[0] = getIntent().getStringExtra("error");
	  TextView view = (TextView) findViewById(R.id.error_msg);
	  view.setText(errorMessages[0]);
	}
 
}




