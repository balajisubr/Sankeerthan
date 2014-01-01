package com.sankeerthan.search.display;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import com.sankeerthan.MainActivity;
import com.sankeerthan.R;
import com.sankeerthan.display.expand.*;
import com.sankeerthan.model.FavoriteDB;
import com.sankeerthan.tabs.SearchTab;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SeekBar;

@SuppressLint("ValidFragment")
public class BhajanDetailsFragment extends ListFragment implements  OnTouchListener, OnCompletionListener, OnClickListener, OnBufferingUpdateListener {
  	//private final String url = AppConfig.URL + "/play/song.mp3";
	private final String FAV = "Like";
	private final String UNFAV = "Unlike";
    private final String[] keys = new String[]{"bhajan", "raaga", "deity", "lyrics", "meaning", "url"};
    
	private String choice = "";
	private LinkedHashMap<String, String> bhajanDetails = new LinkedHashMap<String, String>();
	private Bundle bundle;
    private Button btn_play;
    private Button btn_fav;
	private SeekBar seekBar;
	private MediaPlayer mediaPlayer;
	private int lengthOfAudio = 0;
	private String bhajanName = "";
    private static Handler handler = null;
    private String bhajanDetailsString ="bhajanDetails"; 
	ProgressDialog pd = null;

	private final Runnable r = new Runnable() {	
    public void run() {
        updateSeekProgress();					
	}};

	public BhajanDetailsFragment()
	{
		
	}
	
	public BhajanDetailsFragment(Bundle bundle) {
    	//Looper.loop();
    	handler = new Handler();
        BhajanDetailsFragment.setHandler(handler);
        this.bundle = bundle;
        setDetails(bundle);  
	 }
	
	public void setDetails(Bundle bundle)
	{
		 for(String s: keys) {
	         String value = bundle.getString(s);
	         if( value == null || (value != null && (value.length()==0 || value.isEmpty()))) 
	        	 value = "No info for " + s;
	         bhajanDetails.put(s, value);
	     }

	     setBhajanDetails(bhajanDetails);
	     setBhajanName(bundle.getString("bhajan"));
	}
	
	public void onCreate(Bundle savedInstanceState) {
		//Bundle details = this.getArguments();
		//setDetails(details);
		super.onCreate(savedInstanceState);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//Looper.prepare();
		//Looper.loop();
		//handler = new Handler();
		//Bundle details = this.getArguments();
		//setDetails(details);
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.row,this.getBhajanDetails());
		//setListAdapter(adapter);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.bhajan_details, container, false);
	    
		Bundle details = new Bundle();
		if(getArguments() != null) {
	    	handler = new Handler();
	        BhajanDetailsFragment.setHandler(handler);
	        details = this.getArguments();
		    setDetails(details);
		}
		LinkedHashMap<String, String> bhajanDetails = getBhajanDetails();
		if(bhajanDetails.size() == 0){
			if(savedInstanceState != null)
			{
				Map<String, String> detailsMap = (Map<String, String>) savedInstanceState.getSerializable("bhajanDetails");
				bhajanDetails.putAll(detailsMap); 
			}			
		}
		
		ArrayList<String> tmp= this.formatListItems(bhajanDetails);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.row,tmp);
		setListAdapter(adapter);
        ExpandableListView expandList = (ExpandableListView) view.findViewById(R.id.ExpList);
        ArrayList<Group> expandListItems = setStandardGroups(bhajanDetails);
        ExpandListAdapter expandListAdapter = new ExpandListAdapter(this.getActivity(), expandListItems);
        expandList.setAdapter(expandListAdapter);

		btn_play = (Button) view.findViewById(R.id.btn_play);
		btn_play.setOnClickListener(this);
	    seekBar = (SeekBar)view.findViewById(R.id.seekBar);
		seekBar.setOnTouchListener(this);
	    btn_fav = (Button) view.findViewById(R.id.btn_fav);
        FavoriteDB.setContext(this.getActivity());
        int count = FavoriteDB.selectBhajan(getBhajanName());
	    this.setFavText(btn_fav.getText().toString());
	    if(count == 0) {
            btn_fav.setText(R.string.favorite);
	    }
	    else {
            btn_fav.setText(R.string.unfavorite);
	    }
	    btn_fav.setOnClickListener(new OnClickListener()
	   	{
	    public void onClick(View v) {
			onClick1();
		}});
	    
	    Button searchBtn = (Button) view.findViewById(R.id.btn_search);
	    searchBtn.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				String message = "Are you sure?";
				Button b = (Button) BhajanDetailsFragment.this.getView().findViewById(R.id.btn_play);
                if(b.getText().toString().equals("Pause"))
                	message = "This will stop the Bhajan. Are you sure?";
                else 
                	message = "Are you sure you want to exit to search page?";
				new AlertDialog.Builder(BhajanDetailsFragment.this.getActivity())
			    .setMessage(message)
			    .setCancelable(true)
			    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int id) {
						  if(mediaPlayer != null )
							  mediaPlayer.stop();
			              FragmentManager manager = BhajanDetailsFragment.this.getFragmentManager();
			              FragmentTransaction ft = manager.beginTransaction();
			              ft.replace(android.R.id.content, new SearchTab());
			              ft.commit();   	
			        }
			    })
			    .setNegativeButton("No", null)
			    .show();
			}  	
	    });

		return view;
	}
	
	
	
	
	public void onClick1() {
		String regex = "^No info for.*";
		String name = getBhajanDetail("bhajan");
		if(name == null || name.matches(regex))
			return;
		Button favButton = (Button) (getView().findViewById(R.id.btn_fav));
		String buttonText = favButton.getText().toString();
        FavoriteDB.setContext(this.getActivity());
        if(buttonText.equals(FAV)) {
            FavoriteDB.addBhajan(this.getBhajanName());
            favButton.setText(UNFAV);
        }
        else {
        	FavoriteDB.removeBhajan(this.getBhajanName());
        	favButton.setText(FAV);

        }
	}

	public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
		seekBar.setSecondaryProgress(percent);
	}

	public void onCompletion(MediaPlayer mp) {
		btn_play.setText(R.string.play);
		seekBar.setProgress(0);
	}

	public boolean onTouch(View v, MotionEvent event) {
			SeekBar tmpSeekBar = (SeekBar) v;
			mediaPlayer.seekTo((lengthOfAudio / 100) * tmpSeekBar.getProgress() );
    		return false;
	}


	private void updateSeekProgress() {	 
		if (mediaPlayer.isPlaying()) {
			seekBar.setProgress((int)(((float)mediaPlayer.getCurrentPosition() / lengthOfAudio) * 100));
			getHandler().postDelayed(r, 2000);
		 }
	}
	
	public void onClick(View view) {
    		Button playButton = (Button)view;
    		if(playButton.getText().toString().equals("Play")) {
    		    if(mediaPlayer == null) {
    		        mediaPlayer = new MediaPlayer();
    		        mediaPlayer.setOnBufferingUpdateListener(this);
    		        mediaPlayer.setOnCompletionListener(this);
    		        new PreparePlayer().execute();
		        }
    		    else
    		    {   
    		    	playAudio();
    		    	btn_play.setText(R.string.pause);
    		    }
    		}
    		else {
   			 if(mediaPlayer.isPlaying()){
				 btn_play.setText(R.string.play);
				 pauseAudio();
			  }
   			 }  
    		
     updateSeekProgress();
	}
	
	public void onDestroy() {
		if(mediaPlayer != null) mediaPlayer.stop();	
		super.onDestroy();
	}
	
	public void onDetach()
	{
		super.onDetach();		
	}

	private void pauseAudio() {
		mediaPlayer.pause();
	}

	private void playAudio() {
		mediaPlayer.start();
	}
	
    public LinkedHashMap<String, String> getBhajanDetails() {
        return bhajanDetails;
    }
    
    public String getBhajanDetail(String key)
    {
    	LinkedHashMap<String, String> localBhajanDetails = getBhajanDetails();
    	return localBhajanDetails.get(key);
    }

    public void setBhajanDetails(LinkedHashMap<String, String> string) {
    	this.bhajanDetails = string ;
    }
    
    public String getBhajanName(){
    	return bhajanName;	
    }
    
    public void setBhajanName(String bhajanName) {
    	this.bhajanName = bhajanName;
    }
	
	public void setFavText(String s) {
		this.choice = s;
	}
	
	public String getFavText() {
		if(choice.equals(FAV))
			return FAV;
		else 
			return UNFAV;
	}
	
	public ArrayList<String> formatListItems(LinkedHashMap<String, String> details)
	{
		ArrayList<String> arDetails = new ArrayList<String>();
		Iterator<Entry<String, String>> i = details.entrySet().iterator();
		while (i.hasNext())
		{		
	     Entry<String, String> entry = i.next();
	     String value = "";
	     String key = entry.getKey();
	     String defaultValue = "No info for " + key;
	     if(key.equals("lyrics") || key.equals("meaning") || key.equals("url")){
	    	 continue;
	     }
	     if(entry.getValue() == null)
	    	 value = defaultValue;
	     else
	    	 value = entry.getValue();
		 String detail = entry.getKey().toUpperCase(Locale.US) + ": " + value + "\n";
		 arDetails.add(detail);
		}
		return arDetails;
	}
	
	
	public ArrayList<Group> setStandardGroups(LinkedHashMap<String, String> details) {
	    ArrayList<Group> titles = new ArrayList<Group>();
	    ArrayList<Child> content = new ArrayList<Child>();
	    Group lyricsGroup = new Group();
	    lyricsGroup.setName("  ►LYRICS: ");
	    String[] lyrics = formatLyrics(details.get("lyrics"));
	    for(int i = 0; i < lyrics.length; i++){
	    	Child c = new Child();
	    	c.setName(lyrics[i]);
	    	c.setTag(null);
	    	content.add(c);
	    }
	    lyricsGroup.setItems(content);
        content = new ArrayList<Child>();

	    Group meaningGroup = new Group();
	    meaningGroup.setName("  ►MEANING: ");
	    Child meaning = new Child();
	    meaning.setName(details.get("meaning"));
	    meaning.setTag(null);
	    content.add(meaning);
	    meaningGroup.setItems(content);
	    titles.add(lyricsGroup);
	    titles.add(meaningGroup);
	    return titles;
	}
	
	public String[] formatLyrics(String lyrics) {
	 	String[] formattedLyrics = lyrics.split(";");
	 	return formattedLyrics;
	}
	
	public static Handler getHandler()
	{
		if(handler == null)
		{
			handler = new Handler();
			setHandler(handler);
		}
		
			return handler;
	}
	
	public static void setHandler(Handler handler)
	{
		BhajanDetailsFragment.handler = handler;
	}
	
	public void onSaveInstanceState(Bundle outState){
		MainActivity act = (MainActivity) this.getActivity();
		act.activeFragment = "details";
		outState.putSerializable("bhajanDetails", (Serializable) getBhajanDetails());
		super.onSaveInstanceState(outState);

	}
	
	 class PreparePlayer extends AsyncTask<Void, Void, Void> {
	        ProgressDialog pd = null;
	        String param = "";
	        
	        public void onPreExecute() {
	         	getActivity().runOnUiThread(new Runnable() {
	                public void run() {
		    				pd = new ProgressDialog(BhajanDetailsFragment.this.getActivity());
		    				pd.setTitle("Loading Audio..");
		    				pd.setMessage("Please wait.");
		    				pd.setCancelable(false);
		    				pd.setIndeterminate(true);
		    				pd.show();
		    	        }});
	         	}

	     
	 		protected Void doInBackground(Void... params) {
				try {
					mediaPlayer.setDataSource(bhajanDetails.get("url"));
				    mediaPlayer.prepare();
    			    lengthOfAudio = mediaPlayer.getDuration();
                    playAudio();  

				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                return null;

	 		}
	 		
	 		public void onPostExecute(Void a) {
	 			getActivity().runOnUiThread(new Runnable(){
	 				public void run(){
		    				if(pd != null) 
	                             pd.dismiss();
		    				Button playButton = (Button) getView().findViewById(R.id.btn_play);
		    				playButton.setText(R.string.pause);
		    				updateSeekProgress();
	 				}
	 			});
	 		}	
	     }
	
}
