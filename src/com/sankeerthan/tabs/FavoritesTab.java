package com.sankeerthan.tabs;

import java.util.ArrayList;

import com.sankeerthan.R;
import com.sankeerthan.display.SankeerthanDialog;
import com.sankeerthan.model.Bhajan;
import com.sankeerthan.model.FavoriteDB;
import com.sankeerthan.search.SearchBhajan;
import com.sankeerthan.search.display.BhajanDetailsFragment;

import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
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
		View view = inflater.inflate(R.layout.raaga_deity_list, container, false);	
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
	    				pd.setMessage("Please wait.");
	    				pd.setCancelable(false);
	    				pd.setIndeterminate(true);
	    				pd.show();
	    	        }});

	        }
		protected SearchBhajan doInBackground(String... name) {
	        SearchBhajan searchBhajan = new SearchBhajan();
			try {
				 if(!name[0].equals("No Favorites")){
				     searchBhajan = new SearchBhajan(name[0]);
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
			
	    	Bundle bundle = new Bundle();
			
			if(searchBhajan.serverErrors.size() > 0) {
				// To do : Concat the server errors.
				serverError = searchBhajan.serverErrors.get(0);
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
	         	   AlertDialog alert = SankeerthanDialog.getAlertDialog(FavoritesTab.this.getActivity(), serverError);
	         	   alert.show();

	     	//	Toast.makeText(FavoritesTab.this.getActivity()
			//    		, "An error occured while fetching data. Please check connection or try again later."
			//  		, Toast.LENGTH_LONG).show();
	            if(pd != null){
	        	    pd.dismiss();
	            }			
			return;
			}
			
			if(serverError.length() > 0){
         	   AlertDialog alert = SankeerthanDialog.getAlertDialog(FavoritesTab.this.getActivity(), serverError);
         	   alert.show();
			 //Toast.makeText(FavoritesTab.this.getActivity(), serverError, Toast.LENGTH_LONG).show();
	            if(pd != null){
	        	    pd.dismiss();
	            }			
			return;
			}
				
	        {
			Activity activity = FavoritesTab.this.getActivity();
			Tab tab= activity.getActionBar().getTabAt(1);
			//Looper.prepare();
			tab.setTabListener(new TabsListener<BhajanDetailsFragment>(
					activity, "Bhajan Details", BhajanDetailsFragment.class, bundle));
			activity.getActionBar().setSelectedNavigationItem(1);
	        }
	        if(pd != null){
	        	pd.dismiss();
	        }			
		}
		
		
		
		}

}
