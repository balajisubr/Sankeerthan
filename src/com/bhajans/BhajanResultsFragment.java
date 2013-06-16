package com.bhajans;

import java.util.ArrayList;

import com.bhajans.model.Bhajan;
import com.bhajans.search.SearchBhajan;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.app.ListFragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
 
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.EditText;

@SuppressLint("ValidFragment")
public class BhajanResultsFragment extends ListFragment {
	protected Bundle bundle;
    ArrayList<String> bhajans = new ArrayList<String>();
	String[] Bhajans = new String[10]; {
	for ( int i = 0; i< 10; i++){
		Bhajans[i] = "";
    }}
	
	
	public BhajanResultsFragment()
	{}
	
	public BhajanResultsFragment(Bundle bundle) {
		this.bundle =bundle;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.row,bhajans);
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
 
		listView.setOnItemClickListener(new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    	Bundle bundle = new Bundle();
			String name = ((TextView) view).getText().toString();
	        SearchBhajan searchBhajan = null;
			try {
				searchBhajan = new SearchBhajan(name);
				searchBhajan.getData();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Bhajan result = searchBhajan.result;
	    	android.app.FragmentManager fragmentManager = (getActivity()).getFragmentManager(); 
            bundle.putString("raaga", result.raaga);
            bundle.putString("lyrics", result.lyrics);
            bundle.putString("meaning", result.meaning);
            bundle.putString("deity", result.deity);
            bundle.putString("bhajan", result.name);
            BhajanDetailsFragment fragment = new BhajanDetailsFragment(bundle);
	        FragmentTransaction ft = fragmentManager.beginTransaction();
	        ft.replace(android.R.id.content, fragment).addToBackStack( "search" );
	        //fragmentManager.addOnBackStackChangedListener(null);
	        ft.commit();
	        fragmentManager.executePendingTransactions();      //

		     /*   
		        Intent intent = new Intent(BhajanResultsActivity.this,ODisplayBhajanDetails.class);
		        Bundle myData = new Bundle();
		        myData.putString("raaga", result.raaga);
		        myData.putString("lyrics", result.lyrics);
		        myData.putString("meaning", result.meaning);
		        myData.putString("deity", result.deity);
		        intent.putExtras(myData);
		        view.getContext().startActivity(intent);				
			 */
			}
		});
		setListAdapter(adapter);
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.raaga_deity_list, container, false);
	    setBhajans(bundle.getStringArray("bhajan"));
		return view;
	}
	
	public void setBhajans(String bhajans[]) {
		int i;
		for(i=0;i<bhajans.length;i++) {
			 this.bhajans.add(bhajans[i]);	 
		}
	}
	
	public ArrayList<String> getBhajans() {
		if(bhajans.size() == 0) {
			int i;
			for(i=0;i<4;i++)
	              bhajans.add("No Data found");
	    }
	    return bhajans;
	}
 
}




