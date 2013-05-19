package com.bhajans;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

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

import com.bhajans.display.GenericDisplay;
import com.bhajans.lookup.BhajanLookup;
import com.bhajans.lookup.DeityLookup;
import com.bhajans.lookup.LookUpData;
import com.bhajans.lookup.RaagaLookup;
import com.bhajans.model.Bhajan;
import com.bhajans.display.*;
import com.bhajans.search.*;

@TargetApi(11)
public class MainActivity2 extends Fragment {
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
	
	
	public void onCreate(Bundle savedInstanceState) {
    	System.out.println("IN ACTIVITY ONCREATE");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);

		super.onCreate(savedInstanceState);
        LookUpData.setContext(this.getActivity());     
		this.bhajanNames = LookUpData.getData("bhajans");
	    this.raagaNames = LookUpData.getData("raagas");
		this.deityNames = LookUpData.getData("deities");
        if(!(bhajanNames == null))
        {
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
	   	search.setOnClickListener(new OnClickListener()
	   	{
	     public void onClick(View v)
	     {
	   	 try {
			onClick1();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     }
	   	});
	     commonSearchField = (AutoCompleteTextView) view.findViewById(R.id.editText1);
	
	     if(arrayResponse!=null)
	     {
	     commonAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, arrayResponse);
	     commonSearchField.setAdapter(commonAdapter);
	     }
	     
	     RadioButton raaga = (RadioButton) view.findViewById(R.id.raaga_radio);
	     RadioButton bhajan = (RadioButton) view.findViewById(R.id.bhajan_radio);
	     RadioButton deity = (RadioButton) view.findViewById(R.id.deity_radio);
	       
	     OnClickListener l1 = new OnClickListener(){
	       public void onClick(View v) {
	    	   commonSearchField.setText("");
	         update_data("raagas");
	      }
	     };
	    		
	     OnClickListener l2 = new OnClickListener(){
	  	   public void onClick(View v) {
	  		 commonSearchField.setText("");
	  	     update_data("bhajans");   
	  	    }
	  	   };
	  	   
	  	 OnClickListener l3 = new OnClickListener(){
	  	   public void onClick(View v)
	  	   {
	  		 commonSearchField.setText("");
	  		 update_data("deities");
	  	   }
	  	  };
	  	  		
	   raaga.setOnClickListener(l1);
	   bhajan.setOnClickListener(l2);
	   deity.setOnClickListener(l3);
	   
	   		
		return view;
	}
      
	   public void setClickView(View v)
	   {
		 this.clickView = v;  
	   }
	   
	   public View getClickView()
	   {
		 return clickView;   
	   }

	    
       public void update_data(String key)
         {
    	  commonAdapter.clear();       
          if(key.equals("raagas") && raagaNames!=null) 
          {
		    commonAdapter.addAll(raagaNames);	
	      } 
          else if(key.equals("bhajans") && bhajanNames != null)
          {  
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
       
       
       public void onClick1() throws ClientProtocolException, IOException, JSONException, InterruptedException {
      	View view = this.getView();
      // 	if(view.getId() == R.id.button1)
          {
       		System.out.println("inside button1");

          EditText text = (EditText) getView().findViewById(R.id.editText1);
          
           RadioGroup radio_group = (RadioGroup) view.findViewById(R.id.radioGroup1);
           if(radio_group == null) System.out.println("radio group is null");
           int checked_radio_id = radio_group.getCheckedRadioButtonId();
           //RadioButton raaga_radio = (RadioButton) view.findViewById(R.id.raaga_radio);
           //RadioButton bhajan_radio = (RadioButton) view.findViewById(R.id.bhajan_radio);
           //RadioButton deity_radio = (RadioButton) view.findViewById(R.id.deity_radio);
           //boolean is_raaga_checked = raaga_radio.isChecked();     
           //boolean is_bhajan_checked = bhajan_radio.isChecked();
           //boolean is_deity_checked = deity_radio.isChecked();
               
           if (text.getText().length() == 0) {
         	String error_message = "Please enter valid data";//" + (is_raaga_checked ? "raaga" : "bhajan");
             Toast.makeText(this.getActivity(), error_message, Toast.LENGTH_LONG).show();
             return;
             }
          
          // pd = ProgressDialog.show(this, "Loading..", "Fetching Data", true,
      		//		false);
           
       //    pd.setCancelable(true);
           

           switch(checked_radio_id){
           case R.id.deity_radio:
       	  	SearchDeity searchDeity = new SearchDeity(text.getText().toString());
       	  	searchDeity.getData();
             GenericDisplay deityDisplay = new GenericDisplay(searchDeity, this);
             deityDisplay.processErrorsOrDisplay();
         	break;
           case R.id.raaga_radio:
         	SearchRaaga searchRaaga = new SearchRaaga(text.getText().toString());
         	searchRaaga.getData();
             GenericDisplay raagaDisplay = new GenericDisplay(searchRaaga, this);
             raagaDisplay.processErrorsOrDisplay();
         	break;
           case R.id.bhajan_radio:
      	     System.out.println("here -3");
           	SearchBhajan searchBhajan = new SearchBhajan(text.getText().toString());
           	searchBhajan.getData();
     	     System.out.println("here -2");

             GenericDisplay bhajanDisplay = new GenericDisplay(searchBhajan, this);
     	     System.out.println("here -1");

             bhajanDisplay.processErrorsOrDisplay();
     	     System.out.println("here 0");

         	break;
           }
           }
          /*
            new Thread(new Runnable() {
         	   public void run()
         	   {
         	     // do the thing that takes a long time

         	     runOnUiThread(new Runnable() {
         	       public void run()
         	       {
         	         pd.dismiss();
         	       }
         	     });
         	   }
         	 }).start();*/} 
         
       
     public void afterTextChanged(Editable s) {}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

	public void onTextChanged(CharSequence s, int start, int before, int count) {}
   }
