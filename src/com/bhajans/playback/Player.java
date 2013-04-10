/*
package com.bhajans.playback;

import com.bhajans.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.SeekBar;

public class Player extends Activity implements OnClickListener, OnTouchListener, OnCompletionListener, OnBufferingUpdateListener{
    private Button btn_play;
	private SeekBar seekBar;
	private MediaPlayer mediaPlayer;
	private int lengthOfAudio;
	private int length=0;
	private final String URL = "http://192.168.1.68:3000/play/song.mp3";
    private final Handler handler = new Handler();
	private final Runnable r = new Runnable() {	
		public void run() {
			updateSeekProgress();					
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);
        init();
    }

	private void init() {
		btn_play = (Button)findViewById(R.id.btn_play);
		btn_play.setOnClickListener(this);
		//btn_pause = (Button)findViewById(R.id.btn_pause);
		//btn_pause.setOnClickListener(this);
		//btn_pause.setEnabled(false);
		//btn_stop = (Button)findViewById(R.id.btn_stop);
		//btn_stop.setOnClickListener(this);
		//btn_stop.setEnabled(false);
		
		seekBar = (SeekBar)findViewById(R.id.seekBar);
		seekBar.setOnTouchListener(this);
		
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnBufferingUpdateListener(this);
		mediaPlayer.setOnCompletionListener(this);
				
	}

	public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
		seekBar.setSecondaryProgress(percent);
	}

	public void onCompletion(MediaPlayer mp) {
		btn_play.setText("play");
	}

	public boolean onTouch(View v, MotionEvent event) {
		if (mediaPlayer.isPlaying()) {
			SeekBar tmpSeekBar = (SeekBar) v;
			mediaPlayer.seekTo((lengthOfAudio / 100) * tmpSeekBar.getProgress() );
			System.out.println("lengthofAudio is" + lengthOfAudio);
		}
		return false;
	}

	public void onClick(View view) {

		try {
			mediaPlayer.setDataSource(URL);
			mediaPlayer.prepare();
			lengthOfAudio = mediaPlayer.getDuration();
		} catch (Exception e) {
			//Log.e("Error", e.getMessage());
		}
		seekBar.setProgress((int)(((float)mediaPlayer.getCurrentPosition() / lengthOfAudio) * 100));
		handler.postDelayed(r, 1000);

		switch (view.getId()) {
		case R.id.btn_play:
			if(mediaPlayer.isPlaying()){
				btn_play.setText("play");
				pauseAudio();
				 length=mediaPlayer.getCurrentPosition();
			}
			else
			{
				SeekBar tmpSeekBar = (SeekBar) findViewById(R.id.seekBar);
				mediaPlayer.seekTo((lengthOfAudio / 100) * tmpSeekBar.getProgress() );
				playAudio();
				btn_play.setText("pause");
			}
						break;
		case R.id.btn_pause:
			pauseAudio();
			break;
		case R.id.btn_stop:
			stopAudio();
			break;
		default:
			break;
		}
		
		updateSeekProgress();
	}

	private void updateSeekProgress() {
		 {
				if (mediaPlayer.isPlaying()) {

			seekBar.setProgress((int)(((float)mediaPlayer.getCurrentPosition() / lengthOfAudio) * 100));
			handler.postDelayed(r, 20000);
				}
		}
	}

	private void stopAudio() {
		mediaPlayer.stop();
	}

	private void pauseAudio() {
		mediaPlayer.pause();
	}

	private void playAudio() {
		mediaPlayer.start();
	}
}
*/
