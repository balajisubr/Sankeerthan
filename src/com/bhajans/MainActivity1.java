
 package com.bhajans;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
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
import android.widget.RadioGroup;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.bhajans.display.ODisplayBhajanDetails;
import com.bhajans.display.GenericDisplay;
import com.bhajans.lookup.BhajanLookup;
import com.bhajans.model.Bhajan;
import com.bhajans.search.*;

public class MainActivity1 extends Activity {
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        Tab tab = actionBar.newTab()
                .setText("MainActivity")
                .setTabListener(new TabsListener<MainActivity2>(
                        this, "artist", MainActivity2.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
            .setText("Hi")
            .setTabListener(new TabsListener(
                    this, "top", OPlayerFragment.class));
        actionBar.addTab(tab);

      //  setContentView(R.layout.activity_main);
       
    }
}
 