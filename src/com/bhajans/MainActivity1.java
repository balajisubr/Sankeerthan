/*
 package com.bhajans;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.bhajans.display.DisplayBhajanDetails;
import com.bhajans.lookup.BhajanLookup;
import com.bhajans.model.Bhajan;
import com.bhajans.search.*;

public class MainActivity extends Activity implements TextWatcher {
	public ArrayAdapter<String> adap;
	public String[] data=new String[]{"",""};
    private EditText text;
    String arrayResponse[]=new String[]{"",""};
	AutoCompleteTextView myAutoComplete;
	String item[]={
	  "January", "February", "March", "April",
	  "May", "June", "July", "August",
	  "September", "October", "November", "December"
	};
	final String raagas[]=new String[]{"Kalyani", "Charukesi"};
    final String bhajans[]=new String[]{"Prema Mudita","Rama"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayResponse = bhajans.clone();
        /*
		try {
	
			ArrayList<String> response = BhajanLookup.lookupBhajans();
	         arrayResponse = new String[response.size()];
	        for (int i=0;i<arrayResponse.length;i++) arrayResponse[i]=response.toArray()[i].toString();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

        //setContentView(R.layout.activity_main);
	       myAutoComplete = (AutoCompleteTextView)findViewById(R.id.editText1);
	        adap = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrayResponse);
	       //myAutoComplete.addTextChangedListener(this);
	       myAutoComplete.setAdapter(adap);
	       RadioButton raaga = (RadioButton) findViewById(R.id.raaga_radio);
	       RadioButton bhajan = (RadioButton) findViewById(R.id.bhajan_radio);
	       
	       OnClickListener l1 = new OnClickListener(){
	    	   public void onClick(View v) {
	    		   update_data("raagas");
	    		   	    		 }
	    		};
	    		
	    		 OnClickListener l2 = new OnClickListener(){
	  	    	   public void onClick(View v) {
	  	    		update_data("bhajans");   
	  	    	   }
	  	    		};
	    		
	    		
	       
	       raaga.setOnClickListener(l1);
	       bhajan.setOnClickListener(l2);
	       

        text = (EditText) findViewById(R.id.editText1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void update_data(String key)
    {
     if(key=="raagas")
    	data =  raagas.clone();
     else
    	 data = bhajans.clone();
     arrayResponse[0] = "";
     arrayResponse[1] = "";
     for(int i=0;i<data.length;i++)
     {
    	 System.out.println(data[0] +" " + data[1]);
    	 arrayResponse[i]=data[i];
     }
     adap.clear();
     adap.insert(arrayResponse[0], 0);
     adap.insert(arrayResponse[1], 1);
     adap.setNotifyOnChange(true);
     adap.notifyDataSetChanged();
    }
    
    public void onClick(View view) throws ClientProtocolException, IOException, JSONException, InterruptedException {
      RadioButton raaga_radio = (RadioButton) findViewById(R.id.raaga_radio);
      RadioButton bhajan_radio = (RadioButton) findViewById(R.id.bhajan_radio);
      boolean is_raaga_checked = raaga_radio.isChecked();     
          
    	if (text.getText().length() == 0) {
    	  String error_message = "Please enter a valid " + (is_raaga_checked ? "raaga" : "bhajan");
          Toast.makeText(this, error_message, Toast.LENGTH_LONG).show();
          return;
         }
        Bundle myData = new Bundle();
        Intent intent = null;
    	if(is_raaga_checked)
    	{	
    	SearchRaaga searchRaaga = new SearchRaaga(text.getText().toString());
    	ArrayList<String> response = searchRaaga.result;//SearchRaaga.searchRaaga(text.getText().toString()); //: searchBhajans();
    	System.out.println("The bhajan in MainActivity is" + response.get(0) + response.get(1));
        intent = new Intent(MainActivity.this,BhajanResultsActivity.class);
        //intent.setClassName(MainActivity.this,"com.bhajans.BhajanResultsActivity");
        String arrayResponse[] = new String[response.size()];
        for (int i=0;i<arrayResponse.length;i++) arrayResponse[i]=response.toArray()[i].toString();
        myData.putStringArray("bhajan", arrayResponse);
    	}
    	else
    	{
        SearchBhajan searchBhajan = new SearchBhajan(text.getText().toString());
        Bhajan result = searchBhajan.result;
    	System.out.println("The response for searchBhajan raaga in MainActivity is" + result.raaga );
    	System.out.println("The response for searchBhajan meaning in MainActivity is" + result.meaning );
    	System.out.println("The response for searchBhajan lyrics in MainActivity is" + result.lyrics );

        intent = new Intent(MainActivity.this,DisplayBhajanDetails.class);
        myData.putString("raaga", result.raaga);
        myData.putString("lyrics", result.lyrics);
        myData.putString("meaning", result.meaning);
    	}
        intent.putExtras(myData);
        this.startActivity(intent);
    	}

	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}

	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
      }
 */