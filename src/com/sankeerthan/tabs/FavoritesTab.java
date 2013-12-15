package com.sankeerthan.tabs;

import java.util.ArrayList;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sankeerthan.R;
import com.sankeerthan.Sankeerthan;
import com.sankeerthan.display.SankeerthanDialog;
import com.sankeerthan.model.Bhajan;
import com.sankeerthan.model.FavoriteDB;
import com.sankeerthan.search.SearchBhajan;
import com.sankeerthan.search.display.BhajanDetailsFragment;

import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FavoritesTab extends ListFragment{
	private final String[] keys = new String[]{"bhajan", "raaga", "deity", "lyrics","meaning", "url"};
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
        for(int i=0;i < 30;i++) 
        	bhajans.add("Bhajan " + i);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.row,bhajans);
		ListView listView = getListView();
		listView.setOnItemClickListener(new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String name = ((TextView) view).getText().toString();
            if(name.equals("No Favorites")) return;
                else
            new FetchBhajan().execute(new String[]{name});
            }});

		listView.setTextFilterEnabled(true);
		//});
		setListAdapter(adapter);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fav_tab, container, false);	
		 AdView adView = (AdView) view.findViewById(R.id.adView);
		    //adView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
		    AdRequest adRequest = new AdRequest.Builder()
		    .addTestDevice("C44657E689703A7181A73E789923CF83")
		    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)       // Emulator
		    .build();
		    
		    adView.loadAd(adRequest);
		return view;
	}
	
	
	
    class FetchBhajan extends AsyncTask<String, Void, SearchBhajan>
    {
    	ProgressDialog pd = null;
    	
	    public void onPreExecute() {
	        	getActivity().runOnUiThread(new Runnable() {
	    	        public void run() {
	    				pd = new ProgressDialog(FavoritesTab.this.getActivity());
	    				pd.setTitle("Processing...");
	    				pd.setMessage("Fetching Bhajan details.");
	    				pd.setCancelable(false);
	    				pd.setIndeterminate(true);
	    				pd.show();
	    	        }});

	        }
		protected SearchBhajan doInBackground(String... name) {
	        SearchBhajan searchBhajan = new SearchBhajan();
			try {
				 if(!name[0].equals("No Favorites")){
				     searchBhajan = new SearchBhajan(name[0], false);
			 	     searchBhajan.getData();
				 }
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return searchBhajan;
		}
		
		public void onPostExecute(SearchBhajan searchBhajan)
		{   
			String[] keys = new String[]{"bhajan", "raaga", "deity", "lyrics","meaning", "url"};
			String serverError = "";
			
			Bhajan result = searchBhajan.result;

	    	final Bundle bundle = new Bundle();
			
			if(searchBhajan.serverErrors.size() > 0) {
				serverError = Sankeerthan.formatServerErrors(searchBhajan.serverErrors);
			}
			
			if(result != null){
			   bundle.putString("raaga", result.raaga);
               bundle.putString("lyrics", result.lyrics);
               bundle.putString("meaning", result.meaning);
               bundle.putString("deity", result.deity);
               bundle.putString("bhajan", result.name);
               bundle.putString("url", result.url);
			}

			if(bundle.isEmpty()){
	            AlertDialog alert = SankeerthanDialog.getAlertDialog(FavoritesTab.this.getActivity(), "The bhajan may not exist anymore. Please delete it.");
	            alert.show();
	            
	            if(pd != null){
	        	    pd.dismiss();
	            }			
			return;
			}
			
			if(serverError.length() > 0){
         	   AlertDialog alert = SankeerthanDialog.getAlertDialog(FavoritesTab.this.getActivity(), serverError);
         	   alert.show();
	            if(pd != null){
	        	    pd.dismiss();
	            }			
			return;
			}
				
	        {
	        	
		    Handler handler = new Handler();
		    handler.post(new Runnable(){

			public void run() {
				// TODO Auto-generated method stub
			    Activity activity = FavoritesTab.this.getActivity();
				activity.getActionBar().   setSelectedNavigationItem(1);			        FragmentManager fragmentManager = activity.getFragmentManager();
			    FragmentTransaction ft = fragmentManager.beginTransaction();
			    ft.replace(((ViewGroup)(getView().getParent())).getId(), new BhajanDetailsFragment(bundle));
			    ft.commit();		
			}
		    	
		    });
		    
			
	        }
	        if(pd != null){
	        	pd.dismiss();
	        }			
		}}}
