package com.example.audio;

import java.io.IOException;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;



public class AudioAppActivity extends Activity  implements android.view.View.OnClickListener {
    /** Called when the activity is first created. */
	private ProgressBar playSeekBar;

    private Button buttonPlay;

    private Button buttonStopPlay;

    private MediaPlayer player;

     
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        playSeekBar = (ProgressBar) findViewById(R.id.progressbar1);
        initializeUIElements();

        initializeMediaPlayer();
    }
    private void initializeUIElements() {

        
        playSeekBar.setMax(100);
        playSeekBar.setVisibility(View.INVISIBLE);

        buttonPlay = (Button) findViewById(R.id.Button01);
        buttonPlay.setOnClickListener( this);

        buttonStopPlay = (Button) findViewById(R.id.button2);
        buttonStopPlay.setEnabled(false);
        buttonStopPlay.setOnClickListener(this);

    }

    public void onClick(View v) {
        if (v == buttonPlay) {
            startPlaying();
        } else if (v == buttonStopPlay) {
            stopPlaying();
        }
    }

    private void startPlaying() {
        buttonStopPlay.setEnabled(true);
        buttonPlay.setEnabled(false);

        playSeekBar.setVisibility(View.VISIBLE);

        player.prepareAsync();

        player.setOnPreparedListener(new OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {
                player.start();
            }
        });

    }

    private void stopPlaying() {
        if (player.isPlaying()) {
            player.stop();
            player.release();
            initializeMediaPlayer();
        }

        buttonPlay.setEnabled(true);
        buttonStopPlay.setEnabled(false);
        playSeekBar.setVisibility(View.INVISIBLE);
    }

    private void initializeMediaPlayer() {
        player = new MediaPlayer();
        try {
            player.setDataSource("http://www.youtube.com/watch?v=Iu8210k9WQc&feature=relmfu");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {

            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                playSeekBar.setSecondaryProgress(percent);
                Log.i("Buffering", "" + percent);
            }
        });
    }

     
    protected void onPause() {
        super.onPause();
        if (player.isPlaying()) {
            player.stop();
        }
    }
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
}