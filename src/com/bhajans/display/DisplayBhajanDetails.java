package com.bhajans.display;

import java.util.ArrayList;

import com.bhajans.BhajanDetailsFragment;
import com.bhajans.PlayerFragment;
import com.bhajans.R;
import com.bhajans.model.Bhajan;
import com.bhajans.playback.PlaybackActivity;
import com.bhajans.search.SearchBhajan;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
  ArrayList<String> bhajanDetails = new ArrayList<String>();
  final String keys[] = new String[]{"raaga", "lyrics", "meaning", "deity"};	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		saveBhajanDetails(getIntent());
		  if (savedInstanceState == null) {
				BhajanDetailsFragment fragment = new BhajanDetailsFragment(this.getBhajanDetails());
	            FragmentTransaction ft = getFragmentManager().beginTransaction();
	            ft.attach((Fragment)fragment);
	        } 
	    //this.setContentView(R.layout.list_bhajans);
	    System.out.println("There is no error here!!");
		
		
		//setListAdapter(new ArrayAdapter<String>(this, R.layout.row,bhajanDetails));
 
		//ListView listView = (ListView) this.findViewById(android.R.id.list);//getListView();
		//listView.setTextFilterEnabled(true);
 
 
	}
	
	public void saveBhajanDetails(Intent intent)
	{
	 for(String s: keys)
	 {
	  String value = intent.getStringExtra(s);	 
	  if(s == null || s.isEmpty()) 
		  s = "No data for" + s;
	  bhajanDetails.add(s);
	 }
	 setBhajanDetails(bhajanDetails);
	}

	public ArrayList<String> getBhajanDetails()
	{
	  if(bhajanDetails.size() == 0)
	  { 
		int i;
		for(i=0;i<4;i++)
		  bhajanDetails.add("No Data found");
	  }
	  return bhajanDetails;	
	}
	
	public void setBhajanDetails(ArrayList<String> string)
	{
	 this.bhajanDetails = string ;	
	}
}




