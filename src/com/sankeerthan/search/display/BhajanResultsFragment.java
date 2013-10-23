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
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
				searchBhajan = new SearchBhajan(name);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}/*
			catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				*/
			

	        /*
			try {
				Bhajan result = searchBhajan.result;
				System.out.println("The raaga is " + result.raaga);
				System.out.println("The lyrics is" + result.lyrics);			
				System.out.println("The meaning is" + result.meaning);
	            bundle.putString("raaga", result.raaga);
	            bundle.putString("lyrics", result.lyrics);
	            bundle.putString("meaning", result.meaning);
	            bundle.putString("deity", result.deity);
	            bundle.putString("bhajan", result.name);
	            bundle.putString("url", result.url);

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
*/
			new FetchBhajan().execute(new SearchBhajan[]{searchBhajan});
			/*
        	getActivity().runOnUiThread(new Runnable() {
    	        public void run() {
    				pd = new ProgressDialog(BhajanResultsFragment.this.getActivity());
    				pd.setTitle("Processing...");
    				pd.setMessage("Please wait.");
    				pd.setCancelable(true);
    				pd.setIndeterminate(true);
    				pd.show();
    	        }});
        	
        	*/
	        /*
            BhajanDetailsFragment fragment = new BhajanDetailsFragment(bundle);
	    	android.app.FragmentManager fragmentManager = (getActivity()).getFragmentManager(); 
	        FragmentTransaction ft = fragmentManager.beginTransaction();
	        ft.replace(android.R.id.content, fragment).addToBackStack( "search" );
	        //fragmentManager.addOnBackStackChangedListener(null);
	        ft.commit();
	        fragmentManager.executePendingTransactions();
	        pd.dismiss();
	        */
	             
	        //

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
	    View view = inflater.inflate(R.layout.bhajan_list, container, false);
	    //setBhajans(bundle.getStringArray("bhajan"));
		return view;
	}
	
	public void setBhajans(String bhajans[]) {
		this.bhajans.clear();
		int i;
		for(i=0;i<bhajans.length;i++) {
			 this.bhajans.add(bhajans[i]);	 
		}
		System.out.println("The length of Bhajans in set bhajans is" + this.bhajans.size());
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

	     	//	Toast.makeText(FavoritesTab.this.getActivity()
			//    		, "An error occured while fetching data. Please check connection or try again later."
			//  		, Toast.LENGTH_LONG).show();
	            if(pd != null){
	        	    pd.dismiss();
	            }			
			return;
			}
			
			if(serverError.length() > 0){
         	   AlertDialog alert = SankeerthanDialog.getAlertDialog(BhajanResultsFragment.this.getActivity(), serverError);
         	   alert.show();
			 //Toast.makeText(FavoritesTab.this.getActivity(), serverError, Toast.LENGTH_LONG).show();
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
	        		ft.addToBackStack("list").replace(android.R.id.content, fragment);//.addToBackStack( null );
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
    

 





