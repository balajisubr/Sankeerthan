package com.bhajans;

import com.bhajans.display.DisplayBhajanDetails;
import com.bhajans.model.Bhajan;
import com.bhajans.search.SearchBhajan;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
 
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.EditText;

public class BhajanResultsActivity extends ListActivity {
	String[] Bhajans = new String[10];
	{
	for ( int i = 0; i< 10; i++){
        Bhajans[i] = "";
}
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.raaga_deity_list);
		//Intent intent = getIntent();
		//Bundle bdl = intent.getExtras();
		Bhajans= getIntent().getStringArrayExtra("bhajan");

		setListAdapter(new ArrayAdapter<String>(this, R.layout.row,Bhajans));
 
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
 
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String name = ((TextView) view).getText().toString();
				/**************************************************************************/
		        SearchBhajan searchBhajan = null;
				try {
					searchBhajan = new SearchBhajan(name);
					searchBhajan.getData();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        Bhajan result = searchBhajan.result;

		        Intent intent = new Intent(BhajanResultsActivity.this,DisplayBhajanDetails.class);
		        Bundle myData = new Bundle();
		        myData.putString("raaga", result.raaga);
		        myData.putString("lyrics", result.lyrics);
		        myData.putString("meaning", result.meaning);
		        System.out.println("DEITYYYYYYYY is " + result.deity);		        
		        myData.putString("deity", result.deity);
		        intent.putExtras(myData);
		        view.getContext().startActivity(intent);				
				
/**************************************************************************/
				
			    //Toast.makeText(getApplicationContext(),
				//((TextView) view).getText(), Toast.LENGTH_SHORT).show();
				//Intent int1 = new Intent(BhajanResultsActivity.this,DisplayBhajanDetails.class);
				
				//Bundle bundle = new Bundle();
			}
		});
 
	}
 
}




