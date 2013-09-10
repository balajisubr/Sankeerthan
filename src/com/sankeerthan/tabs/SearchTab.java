package com.sankeerthan.tabs;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
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
import android.content.DialogInterface;

import com.sankeerthan.AppConfig;
import com.sankeerthan.IntroFlashActivity;
import com.sankeerthan.R;
import com.sankeerthan.R.id;
import com.sankeerthan.R.layout;
import com.sankeerthan.display.*;
import com.sankeerthan.lookup.OBhajanLookup;
import com.sankeerthan.lookup.ODeityLookup;
import com.sankeerthan.lookup.ORaagaLookup;
import com.sankeerthan.model.Bhajan;
import com.sankeerthan.model.LookUpData;
import com.sankeerthan.search.*;
import com.sankeerthan.search.display.GenericDisplay;

@TargetApi(11)
public class SearchTab extends Fragment {
	public View view;
	public View clickView;
	public ArrayAdapter<String> commonAdapter;
	public ArrayList<String> data=new ArrayList<String>();
	private EditText text;
	ArrayList<String> arrayResponse=new ArrayList<String>();
	ArrayList<String> bhajanNames;
	ArrayList<String> raagaNames;	
	ArrayList<String> deityNames;
	AutoCompleteTextView commonSearchField;
	ProgressDialog pd;
	public Context context;
	

	public void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);

		super.onCreate(savedInstanceState);
		this.setContext(this.getActivity());
        LookUpData.setContext(this.getActivity());     
		this.bhajanNames = IntroFlashActivity.getLookUpValues(AppConfig.BHAJANS);
	    this.raagaNames = IntroFlashActivity.getLookUpValues(AppConfig.RAAGAS);
		this.deityNames = IntroFlashActivity.getLookUpValues(AppConfig.DEITIES);
		
        if(bhajanNames.size() > 0 && bhajanNames != null) {
        	arrayResponse.clear();
	        arrayResponse.addAll(bhajanNames);
        }
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_main, container, false);
	   	this.view = view;
	  
	   	Button search = (Button) view.findViewById(R.id.button1);
	   	search.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
	   		EditText text1 = (EditText) getView().findViewById(R.id.editText1);
	   		if (text1.getText().length()== 0) {
	   			String error_message = "Please enter valid data";
	   			Toast.makeText(SearchTab.this.getContext(), error_message, Toast.LENGTH_LONG).show();
	   		}
	   		else{
		        AsyncTask<Void, Void, Void> task = new SearchProgress();
		        task.execute();
	   		}
	   	   }
	   	});
	   	commonSearchField = (AutoCompleteTextView) view.findViewById(R.id.editText1);
	
		if(arrayResponse != null && arrayResponse.size() > 0)	{
	    	commonAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, arrayResponse);
	    	commonSearchField.setAdapter(commonAdapter);
	    }
	     
	    RadioButton raaga = (RadioButton) view.findViewById(R.id.raaga_radio);
	    RadioButton bhajan = (RadioButton) view.findViewById(R.id.bhajan_radio);
	    RadioButton deity = (RadioButton) view.findViewById(R.id.deity_radio);
	       
	    OnClickListener l1 = new OnClickListener() {
	    public void onClick(View v) {
	    	commonSearchField.setText("");
	    	updateData("raagas");
	    }};
	    		
	    OnClickListener l2 = new OnClickListener(){
	    public void onClick(View v) {
	    	commonSearchField.setText("");
	    	updateData("bhajans");   
	  	}};
	  	   
	  	OnClickListener l3 = new OnClickListener(){
	  	public void onClick(View v) {
	  	   	commonSearchField.setText("");
	  	   	updateData("deities");
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
	   		commonAdapter.clear();       
	   		if(key.equals("raagas") && raagaNames!=null) {
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
	   	
	   	public void setContext(Context context){
	   		this.context = context;
	   	}
       
	   	public Context getContext(){
	   		return context;
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
   	    				pd.setMessage("Please wait.");
   	    				pd.setCancelable(true);
   	    				pd.setIndeterminate(true);
   	    				pd.show();
   	    	        }});

   	        }
    	
    	    public Void doInBackground(Void... params) {
    	   		//View view = this.getView();
    	  		{
    		    EditText text = (EditText) getView().findViewById(R.id.editText1);
    	   		RadioGroup radio_group = (RadioGroup) getView().findViewById(R.id.radioGroup1);
    	   		int checked_radio_id = radio_group.getCheckedRadioButtonId();
                  	   		
             SearchDeity searchDeity = null;
             SearchRaaga searchRaaga = null;
             SearchBhajan searchBhajan = null;
             try {
    	   	 switch(checked_radio_id){
               case R.id.deity_radio:
    			   searchDeity = new SearchDeity(text.getText().toString());
           	  	   searchDeity.getData();
           	  	   GenericDisplay deityDisplay = new GenericDisplay(searchDeity, SearchTab.this);
           	  	   deityDisplay.processErrorsOrDisplay();
             	   break;
               case R.id.raaga_radio:
            	   searchRaaga = new SearchRaaga(text.getText().toString());
            	   searchRaaga.getData();
            	   GenericDisplay raagaDisplay = new GenericDisplay(searchRaaga, SearchTab.this);
            	   raagaDisplay.processErrorsOrDisplay();
            	   break;
               case R.id.bhajan_radio:
            	   searchBhajan = new SearchBhajan(text.getText().toString());
               	   searchBhajan.getData();
               	   GenericDisplay bhajanDisplay = new GenericDisplay(searchBhajan, SearchTab.this);
               	   bhajanDisplay.processErrorsOrDisplay();
               	   break;
    	   	 }}
               catch (InterruptedException e) {
            	   pd.dismiss();
            	   Toast.makeText(context, "An error occurred! Please contact", Toast.LENGTH_SHORT);
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
   	}
