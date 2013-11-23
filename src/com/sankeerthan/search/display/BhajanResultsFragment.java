package com.sankeerthan.search.display;

import java.util.ArrayList;


import com.sankeerthan.R;
import com.sankeerthan.Sankeerthan;
import com.sankeerthan.display.SankeerthanDialog;
import com.sankeerthan.model.Bhajan;
import com.sankeerthan.model.LookUpData;
import com.sankeerthan.search.SearchBhajan;
import com.sankeerthan.tabs.FavoritesTab;
import com.sankeerthan.tabs.SearchTab;
import com.sankeerthan.tabs.TabsListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
 
import android.os.AsyncTask;

@SuppressLint("ValidFragment")
public class BhajanResultsFragment extends ListFragment {
	ProgressDialog pd;
	protected Bundle bundle;
    ArrayList<String> bhajans = new ArrayList<String>();	
	
	public BhajanResultsFragment()
	{}
	
	public BhajanResultsFragment(Bundle bundle) {
		this.bundle =bundle;
	}

	public void onCreate(Bundle savedInstanceState) {
	    setBhajans(bundle.getStringArray("bhajan"));
		super.onCreate(savedInstanceState);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.row,this.getBhajans());
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
 
		listView.setOnItemClickListener(new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String name = ((TextView) view).getText().toString();
			if(name.equals("No Data found")) return;
	    	Bundle bundle = new Bundle();
	        SearchBhajan searchBhajan = new SearchBhajan();
			try {
				searchBhajan = new SearchBhajan(name, false);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			new FetchBhajan().execute(new SearchBhajan[]{searchBhajan});
		
			}
		});
		setListAdapter(adapter);
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.bhajan_list, container, false);
	    Button searchBtn = (Button) view.findViewById(R.id.btn_search);
	    searchBtn.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
              FragmentManager manager = BhajanResultsFragment.this.getFragmentManager();
              FragmentTransaction ft = manager.beginTransaction();
              ft.replace(android.R.id.content, new SearchTab());
              ft.commit();
			}  	
	    });
		return view;
	}
	
	public void setBhajans(String bhajans[]) {
		this.bhajans.clear();
		int i;
		for(i=0;i<bhajans.length;i++) {
			 this.bhajans.add(bhajans[i]);	 
		}
	}
	
	public ArrayList<String> getBhajans() {
		if(bhajans.size() == 0) {
	        bhajans.add("No Data found");
		}
	    return bhajans;
	}
	
    class FetchBhajan extends AsyncTask<SearchBhajan, Void, SearchBhajan>
    {
    	ProgressDialog pd = null;
    	Bundle bundle = new Bundle();
    	
	    public void onPreExecute() {
	        	getActivity().runOnUiThread(new Runnable() {
	    	        public void run() {
	    				pd = new ProgressDialog(BhajanResultsFragment.this.getActivity());
	    				pd.setTitle("Processing...");
	    				pd.setMessage("Please wait.");
	    				pd.setCancelable(false);
	    				pd.setIndeterminate(true);
	    				pd.show();
	    	        }});

	        }
		protected SearchBhajan doInBackground(SearchBhajan... bhajan) {
	        SearchBhajan searchBhajan = bhajan[0];
			searchBhajan.getData();
			return searchBhajan;
		}
		
		public void onPostExecute(SearchBhajan searchBhajan)
		{   		
			String[] keys = new String[]{"bhajan", "raaga", "deity", "lyrics","meaning", "url"};
			String serverError = "";
			
			Bhajan result = searchBhajan.result;
						
			if(searchBhajan.serverErrors.size() > 0) {
				serverError = Sankeerthan.formatServerErrors(searchBhajan.serverErrors);
			}
			
			if(result != null)
			{
            bundle.putString("raaga", result.raaga);
            bundle.putString("lyrics", result.lyrics);
            bundle.putString("meaning", result.meaning);
            bundle.putString("deity", result.deity);
            bundle.putString("bhajan", result.name);
            bundle.putString("url", result.url);
			}

			if(bundle.isEmpty()){
	         	   AlertDialog alert = SankeerthanDialog.getAlertDialog(BhajanResultsFragment.this.getActivity(), serverError);
	         	   alert.show();

	            if(pd != null){
	        	    pd.dismiss();
	            }			
			return;
			}
			
			if(serverError.length() > 0){
         	   AlertDialog alert = SankeerthanDialog.getAlertDialog(BhajanResultsFragment.this.getActivity(), serverError);
         	   alert.show();
	            if(pd != null){
	        	    pd.dismiss();
	            }			
			return;
			}
				
	        {
	        	getActivity().runOnUiThread(new Runnable() {
	        		public void run(){
	        		BhajanDetailsFragment fragment = new BhajanDetailsFragment(bundle);
	        		android.app.FragmentManager fragmentManager = (getActivity()).getFragmentManager();

	        		FragmentTransaction ft = fragmentManager.beginTransaction();
	        		ft.replace(android.R.id.content, fragment).addToBackStack( "list" );
	        		ft.commit();
	        		fragmentManager.executePendingTransactions();
	        		}});
	        		if(pd != null){
	        		pd.dismiss();
	        		}	
	        		}
	        		}		
		}
    }
    

 





