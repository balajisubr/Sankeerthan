package com.sankeerthan.tabs;

import java.util.ArrayList;

import com.sankeerthan.R;
import com.sankeerthan.model.Bhajan;
import com.sankeerthan.model.FavoriteDB;
import com.sankeerthan.search.SearchBhajan;
import com.sankeerthan.search.display.BhajanDetailsFragment;

import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class FavoritesTab extends ListFragment{
	
    ArrayList<String> bhajans = new ArrayList<String>();

	public FavoritesTab(){
		
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
		listView.setOnItemClickListener(new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String name = ((TextView) view).getText().toString();
            navigateToTab(name);			}
			});

		listView.setTextFilterEnabled(true);
		//});
		setListAdapter(adapter);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.raaga_deity_list, container, false);	
		return view;
	}
	
	public void navigateToTab(String name)
	{
		if(name.equals("No Favorites")) return;
		Tab tab= this.getActivity().getActionBar().getTabAt(1);   
        SearchBhajan searchBhajan = null;
		try {
			 searchBhajan = new SearchBhajan(name);
			searchBhajan.getData();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Bhajan result = searchBhajan.result;
		
		if((!searchBhajan.errorMsg.isEmpty() || searchBhajan.serverErrors.size() > 0))
		{
		Toast.makeText(this.getActivity(), "An error occured while fetching data. Please check connection or try again later", Toast.LENGTH_LONG).show();
		return;
	    }
		
		
    	Bundle bundle = new Bundle();
        bundle.putString("raaga", result.raaga);
        bundle.putString("lyrics", result.lyrics);
        bundle.putString("meaning", result.meaning);
        bundle.putString("deity", result.deity);
        bundle.putString("bhajan", result.name);
        bundle.putString("url", result.url);
		tab.setTabListener(new TabsListener<BhajanDetailsFragment>(
                this.getActivity(), "Bhajan Details", BhajanDetailsFragment.class, bundle));
		this.getActivity().getActionBar().setSelectedNavigationItem(1);
		
	}
}
