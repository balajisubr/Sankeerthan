package com.sankeerthan.tabs;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
import com.sankeerthan.Sankeerthan;
import com.sankeerthan.R;
import com.sankeerthan.display.*;
import com.sankeerthan.model.LookUpData;
import com.sankeerthan.search.*;
import com.sankeerthan.search.display.GenericDisplay;

public class SearchTab extends Fragment {
	public View view;
	public View clickView;
	public ArrayAdapter<String> commonAdapter;
	public ArrayList<String> data=new ArrayList<String>();
	ArrayList<String> arrayResponse=new ArrayList<String>();
	ArrayList<String> bhajanNames = new ArrayList<String>(400);
	ArrayList<String> raagaNames  = new ArrayList<String>(400);	
	ArrayList<String> deityNames  = new ArrayList<String>(20);
	AutoCompleteTextView commonSearchField;
	ProgressDialog pd;
	public Context context;
	public static LinkedHashMap<String, ArrayList<String>> lookUpValues = new LinkedHashMap<String, ArrayList<String>>();
	
    public SearchTab() {
    	
    }
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContext(this.getActivity());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        LookUpData.setContext(this.getActivity());
		}

	public void onResume()
	{
		super.onResume();

	}
	
	public void onStart()
	{
		new FetchData().execute();
		super.onStart();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.search_tab, container, false);
	   	this.view = view;
	   	/*
	    AdView adView = (AdView) view.findViewById(R.id.adView);
	    //adView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
	    AdRequest adRequest = new AdRequest.Builder()
	    .addTestDevice("C44657E689703A7181A73E789923CF83")
	    .addTestDevice("565026B667D3010DF7C8542C4E201853")
	    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)       // Emulator
	    .build();
	    
	    adView.loadAd(adRequest);
        */
	   	Button search = (Button) view.findViewById(R.id.button1);
	   	search.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	   		EditText text1 = (EditText) getView().findViewById(R.id.editText1);
			mgr.hideSoftInputFromWindow(text1.getWindowToken(), 0);
	   		if (text1.getText().length()== 0) {
	   			String error_message = "Please enter something before searching.";
	   			Toast.makeText(SearchTab.this.getContext(), error_message, Toast.LENGTH_SHORT).show();
	   		}
	   		else{
	   			String regex = "[()a-zA-Z/\\s-]+";
	   			if(!text1.getText().toString().matches(regex)){
		   			Toast.makeText(SearchTab.this.getContext(), "Please enter valid information.", Toast.LENGTH_SHORT).show();
	   			}
	   			else {
		        AsyncTask<Void, Void, Void> task = new SearchProgress();
		        task.execute();
	   			}
	   		}
	   	   }
	   	});
	   	commonSearchField = (AutoCompleteTextView) view.findViewById(R.id.editText1);
	
		//if(arrayResponse != null && arrayResponse.size() > 0)	{
	    commonAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, arrayResponse);
	    commonSearchField.setAdapter(commonAdapter);
	    //}
	     
	    RadioButton raaga = (RadioButton) view.findViewById(R.id.raaga_radio);
	    RadioButton bhajan = (RadioButton) view.findViewById(R.id.bhajan_radio);
	    RadioButton deity = (RadioButton) view.findViewById(R.id.deity_radio);
	       
	    OnClickListener l1 = new OnClickListener() {
	    public void onClick(View v) {
	    	new FetchChoiceData().execute(new String[]{"raagas"});
	    }};
	    		
	    OnClickListener l2 = new OnClickListener(){
	    public void onClick(View v) {
	    	new FetchChoiceData().execute(new String[]{"bhajans"});
	  	}};
	  	   
	  	OnClickListener l3 = new OnClickListener(){
	  	public void onClick(View v) {
	    	new FetchChoiceData().execute(new String[]{"deities"});
	  	}};
	  	  		
	  	raaga.setOnClickListener(l1);
	  	bhajan.setOnClickListener(l2);
	  	deity.setOnClickListener(l3);
	   	
		return view;
		
		
		}
      
	   	public void setClickView(View v) {
	   		this.clickView = v;  
	   	}
	   
	   	public View getClickView() {
	   		return clickView;   
	   	}

	    
	   	public void updateData(String key) {
	   		if(commonAdapter !=null) { 
	   			if(!commonAdapter.isEmpty())
	   		        commonAdapter.clear();
	   		if(raagaNames != null && key.equals("raagas")) {
	   			commonAdapter.addAll(raagaNames);	
	   		} 
	   		else if(key.equals("bhajans") && bhajanNames != null) {  
	   			commonAdapter.addAll(bhajanNames);
            }
	   		else
	   		{
	   			if(deityNames != null)  
	   				commonAdapter.addAll(deityNames);  
	   		}  
	   		commonAdapter.setNotifyOnChange(true);
	   		commonAdapter.notifyDataSetChanged();
	   		}
	   	}
	   	
	   	public void setContext(Context context){
	   		this.context = context;
	   	}
       
	   	public Context getContext(){
	   		return context;
	   	}
	   	
	    public static void setLookUpValues(String type, ArrayList<String> values)
	    {
	    	lookUpValues.put(type, values);
	    }
	    
	    public static ArrayList<String> getLookUpValues(String type)
	    {
	    	ArrayList<String> result = lookUpValues.get(type);
	    	return result;
	    }

	   	
	    public void afterTextChanged(Editable s) {}
	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	    public void onTextChanged(CharSequence s, int start, int before, int count) {}

	   	
	   	 
	
    public class SearchProgress extends AsyncTask<Void, Void, Void>
    {
       	ProgressDialog pd = null;
    	
    	    public void onPreExecute() {
   	        	getActivity().runOnUiThread(new Runnable() {
   	    	        public void run() {
   	    				pd = new ProgressDialog(SearchTab.this.getContext());
   	    				pd.setTitle("Processing...");
   	    				pd.setMessage("Search in Progress.");
   	    				pd.setCancelable(false);
   	    				pd.setIndeterminate(true);
   	    				pd.show();
   	    	        }});

   	        }
    	
    	    public Void doInBackground(Void... params) {
    	  		{
    		    EditText text = (EditText) getView().findViewById(R.id.editText1);
    	   		RadioGroup radio_group = (RadioGroup) getView().findViewById(R.id.radioGroup1);
    	   		int checked_radio_id = radio_group.getCheckedRadioButtonId();
                  	   		
                SearchDeity searchDeity = null;
                SearchRaaga searchRaaga = null;
                SearchBhajan searchBhajan = null;
                try {
    	   	    if (checked_radio_id == R.id.deity_radio) {
					searchDeity = new SearchDeity(text.getText().toString());
					searchDeity.getData();
					GenericDisplay deityDisplay = new GenericDisplay(searchDeity, SearchTab.this);
					deityDisplay.processErrorsOrDisplay();
				} else if (checked_radio_id == R.id.raaga_radio) {
					searchRaaga = new SearchRaaga(text.getText().toString());
					searchRaaga.getData();
					GenericDisplay raagaDisplay = new GenericDisplay(searchRaaga, SearchTab.this);
					raagaDisplay.processErrorsOrDisplay();
				} else if (checked_radio_id == R.id.bhajan_radio) {
					searchBhajan = new SearchBhajan(text.getText().toString(), false);
					searchBhajan.getData();
					GenericDisplay bhajanDisplay = new GenericDisplay(searchBhajan, SearchTab.this);
					bhajanDisplay.processErrorsOrDisplay();
				}}
               catch (InterruptedException e) {
            	   pd.dismiss();
            	   AlertDialog alert = SankeerthanDialog.getAlertDialog(context, "An error occurred! Please contact us.");
             	   alert.show();
            	   //Toast.makeText(context, "An error occurred! Please contact" , Toast.LENGTH_SHORT).show();
               }
               if(pd !=null){
            	   pd.dismiss();
               }
    	   	}
    	  		return null;
    	   }
    	    
			public void onPostExecute() {
   	        	getActivity().runOnUiThread(new Runnable() {
   	    	        public void run() {
   	    	            if(pd != null){
	    	            	pd.dismiss();
   	    	            }
   	    	    }});
			}
        }
    
    class FetchData extends AsyncTask<Void, Void, Integer>{
        ProgressDialog pd = null;
        Activity act = getActivity();
        public void onPreExecute() {
         	act.runOnUiThread(new Runnable() {
                public void run() {
	    				pd = new ProgressDialog(SearchTab.this.getContext());
	    				pd.setTitle("Loading...");
	    				pd.setMessage("Please Wait.");
	    				pd.setCancelable(false);
	    				pd.setIndeterminate(true);
	    				pd.show();
	    	        }});
         	}

     
 		protected Integer doInBackground(Void... params) {
 			Runnable lookUpBhajans = new Runnable() {
 				public void run()
 				{
 					setLookUpValues(Sankeerthan.BHAJANS, LookUpData.getData(Sankeerthan.BHAJANS));
 				}
 			};
 			
 			Runnable lookUpRaagas = new Runnable() {
 				public void run()
 				{
 					setLookUpValues(Sankeerthan.RAAGAS, LookUpData.getData(Sankeerthan.RAAGAS));
 				}				
 			};
 			
 			Runnable lookUpDeities = new Runnable() {
 				public void run()
 				{
 		        	setLookUpValues(Sankeerthan.DEITIES, LookUpData.getData(Sankeerthan.DEITIES));

 				}				
 			};
         	Thread t1 = new Thread(lookUpBhajans);
         	Thread t2 = new Thread(lookUpRaagas);
         	Thread t3 = new Thread(lookUpDeities);
         	
         	t1.start(); 
         	t2.start();
         	t3.start();
         	
         	try {
 				t1.join();
 	        	t2.join();
 	        	t3.join();
 				
 		        SearchTab.this.bhajanNames = getLookUpValues(Sankeerthan.BHAJANS);
 			    SearchTab.this.raagaNames =  getLookUpValues(Sankeerthan.RAAGAS);
 				SearchTab.this.deityNames =  getLookUpValues(Sankeerthan.DEITIES);
 		} catch (InterruptedException e) {
 				
 			}
         	
 			return 1;
 		}
 		
 		public void onPostExecute(Integer result) {
 			act.runOnUiThread(new Runnable(){
 				public void run(){
 					  if(bhajanNames != null && bhajanNames.size() > 0) {
 	 			        	arrayResponse.clear();
 	 				        arrayResponse.addAll(bhajanNames);
 	 				        commonAdapter = new ArrayAdapter<String>(act, android.R.layout.simple_dropdown_item_1line, arrayResponse);
 	 				        View view = SearchTab.this.getView();
 	 				        if(view != null) {
 	 					   	   commonSearchField = (AutoCompleteTextView) view.findViewById(R.id.editText1);
 	 					       if(commonSearchField != null)
 	 					    	   commonSearchField.setAdapter(commonAdapter);
 	 				        }
 	 			        }
	    				if(pd !=null) 
                             pd.dismiss();
 				}
 			});
 		}
     	
     }
    
    
    class FetchChoiceData extends AsyncTask<String, Void, Integer>{
        ProgressDialog pd = null;
        String param = "";
        
        public void onPreExecute() {
         	getActivity().runOnUiThread(new Runnable() {
                public void run() {
	    				pd = new ProgressDialog(SearchTab.this.getContext());
	    				pd.setTitle("Loading...");
	    				pd.setMessage("Please wait.");
	    				pd.setCancelable(false);
	    				pd.setIndeterminate(true);
	    				pd.show();
	    	        }});
         	}

     
 		protected Integer doInBackground(String... params) {
 		    param = params[0];
 			getActivity().runOnUiThread(new Runnable(){
 				public void run(){
 			    	commonSearchField.setText("");
 			    	updateData(FetchChoiceData.this.param);         	
 				}
 			});

 			return 1;
 		}
 		
 		public void onPostExecute(Integer result) {
 			getActivity().runOnUiThread(new Runnable(){
 				public void run(){
	    				if(pd !=null) 
                             pd.dismiss();
 				}
 			});
 		}
     	
     }

   	}
