package com.bhajans.display;

import com.bhajans.PlayerFragment;
import com.bhajans.R;
import com.bhajans.model.Bhajan;
import com.bhajans.playback.PlaybackActivity;
import com.bhajans.search.SearchBhajan;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
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

public class DisplayBhajanDetails extends ListActivity {
	String[] bhajanDetails = new String[10];
	{
	for ( int i = 0; i< 10; i++){
        bhajanDetails[i] = "";
}
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.list_bhajans);
	    System.out.println("There is no error here!!");
		bhajanDetails[0] = getIntent().getStringExtra("raaga");
	    System.out.println("bhajan de 0 is " + bhajanDetails[0] );
		bhajanDetails[1] = getIntent().getStringExtra("lyrics");
		bhajanDetails[2] = getIntent().getStringExtra("meaning");
		bhajanDetails[3] = getIntent().getStringExtra("deity");			
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.row,bhajanDetails));
 
		ListView listView = (ListView) this.findViewById(android.R.id.list);//getListView();
		listView.setTextFilterEnabled(true);
 
		listView.setOnItemClickListener(new OnItemClickListener() {
			@TargetApi(11)
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String name = ((TextView) view).getText().toString();
				/*
				if (name.equals("Play"))
				{
					PlayerFragment frag = (PlayerFragment) getFragmentManager().findFragmentById(R.id.player_fragment);
					if (frag == null) {
			            FragmentTransaction ft = getFragmentManager().beginTransaction();
			            ft.add(R.id.player_fragment, new PlayerFragment());
			            ft.commit(); 
			        }
				// Intent intent = new Intent(DisplayBhajanDetails.this,PlaybackActivity.class);	
				 //startActivity(intent);
				}
			*/
			}
		});
 
	}
 
}




