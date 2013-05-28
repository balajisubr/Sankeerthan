package com.bhajans.display;

import java.util.ArrayList;

import com.bhajans.R;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FavoriteFragment extends ListFragment{
	
    ArrayList<String> bhajans = new ArrayList<String>();

	public FavoriteFragment(){
		
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		FavoriteDB.setContext(this.getActivity());
		bhajans = FavoriteDB.fetchBhajans();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.row,bhajans);
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		setListAdapter(adapter);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.raaga_deity_list, container, false);	
		return view;
	}
}
