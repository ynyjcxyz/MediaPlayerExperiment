package com.example.android.experiment;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_play;
    private Button button_pause;
    private Button button_stop;
    private MediaPlayer mediaPlayer;
    private boolean isRelease = true;

    private final MediaPlayer.OnCompletionListener mCompletionListener = mediaPlayer -> releaseMediaPlayer();

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            button_play.setEnabled(true);
            button_pause.setEnabled(false);
            button_stop.setEnabled(false);
            mediaPlayer.release();
            isRelease = true;
            mediaPlayer = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blindView_Click();
    }

    private void blindView_Click() {
        button_play = (Button) findViewById(R.id.Play);
        button_pause = (Button) findViewById(R.id.Pause);
        button_stop = (Button) findViewById(R.id.Stop);

        button_pause.setEnabled(false);
        button_stop.setEnabled(false);

        button_play.setOnClickListener(this);
        button_pause.setOnClickListener(this);
        button_stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Play:
                if (isRelease) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.voice);
                    isRelease = false;
                }
                mediaPlayer.start();
                button_play.setEnabled(false);
                button_pause.setEnabled(true);
                button_stop.setEnabled(true);
                mediaPlayer.setOnCompletionListener(mCompletionListener);
                break;
            case R.id.Pause:
                mediaPlayer.pause();
                button_play.setEnabled(true);
                button_pause.setEnabled(false);
                button_stop.setEnabled(false);
                break;
            case R.id.Stop:
                mediaPlayer.reset();
                mediaPlayer.release();
                isRelease = true;
                button_play.setEnabled(true);
                button_pause.setEnabled(false);
                button_stop.setEnabled(false);
                break;
        }
    }
}