package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class MediaPlayerActivity extends AppCompatActivity {

    private MediaPlayer music;
    private SeekBar seekbar;
    private Handler handler;
    private Runnable runnable;
    Button playButton;
    Button stopButton;
    Button pauseButton;
    TextView songTitle;
    String songName;

    public static final String EXTRA_NAME = "song_name";
    int position;
    ArrayList<File> mySongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        music = MediaPlayer.create(this,R.raw.notimetodie);
        seekbar = findViewById(R.id.seekBar);
        songTitle = findViewById(R.id.songTitle);
        pauseButton = findViewById(R.id.pauseButton);
        playButton = findViewById(R.id.playButton);
        stopButton = findViewById(R.id.stopButton);

        if(music != null){
            music.start();
            music.release();
        }

        Intent intent = new Intent();
        Bundle bundle = intent.getExtras();

        mySongs = (ArrayList)bundle.getParcelableArrayList("songs");
        String songName = intent.getStringExtra("songname");
        position = bundle.getInt("position",0);
        songTitle.setSelected(true);
        Uri uri = Uri.parse(mySongs.get(position).toString());
        songName = mySongs.get(position).getName();
        songTitle.setText(songName);

        music = MediaPlayer.create(getApplicationContext(),uri);
        music.start();

        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!music.isPlaying()) {
                    music.start();
                }
            }
        });

    }


}