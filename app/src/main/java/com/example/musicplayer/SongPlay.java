package com.example.musicplayer;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class SongPlay extends AppCompatActivity {

    private ArrayList<String> song_url;
    private Button play, back, next, repeat,randomplayer;
    private TextView songname, finaletime;
    private Boolean Randommusic = false;
    private Boolean Reapet = false;
    private MediaPlayer mp;
    private SeekBar positionBar;
    private Integer totalTime,next_song, prev_song,position,maxRandomnumber;
    private Integer randomnumber = 0;
    private double startTime = 0;
    private Random rd;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mp.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String text = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        maxRandomnumber = intent.getIntExtra(MainActivity.EXTRA_RANDOM_NUMBER,0);
        position = intent.getIntExtra(MainActivity.EXTRA_Number, 0);
        song_url = (ArrayList<String>) getIntent().getSerializableExtra(MainActivity.EXTRA_ARRAY_LIST);
        int raid = getResources().getIdentifier(text, "raw", getPackageName());
        setContentView(R.layout.activity_song_play);
        play = findViewById(R.id.play);
        next = findViewById(R.id.next);
        repeat = findViewById(R.id.repeat);
        finaletime = findViewById(R.id.finaltime);
        back = findViewById(R.id.back);
        positionBar = (SeekBar) findViewById(R.id.seekbar);
        randomplayer = findViewById(R.id.randummuzic);
        songname = findViewById(R.id.songnmae);
        songname.setText(text);
        mp = MediaPlayer.create(this, raid);
        mp.setVolume(0.5f, 0.5f);
        totalTime = mp.getDuration();
        positionBar.setMax(totalTime);
        rd = new Random();
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mp.isPlaying()) {
                    mp.start();
                    play.setBackgroundResource(R.drawable.pause);
                } else {
                    mp.pause();
                    play.setBackgroundResource(R.drawable.play);
                }
            }
        });
        positionBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mp.seekTo(progress);
                    positionBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_song = position + 1;
                if (next_song < song_url.size()) {

                    int rr = getResources().getIdentifier(song_url.get(next_song), "raw", getPackageName());
                    position = next_song;
                    mp.reset();
                    try {
                        songname.setText(song_url.get(next_song));
                        mp = MediaPlayer.create(getApplicationContext(), rr);
                        positionBar.setProgress(0);
                        totalTime = mp.getDuration();
                        mp.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    position = next_song = 0;
                    int rr = getResources().getIdentifier(song_url.get(position), "raw", getPackageName());
                    mp.reset();
                    try {
                        songname.setText(song_url.get(next_song));
                        mp = MediaPlayer.create(getApplicationContext(), rr);
                        positionBar.setProgress(0);
                        totalTime = mp.getDuration();
                        mp.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev_song = position - 1;
                if (prev_song < 0) {
                    prev_song = position = song_url.size();
                }
                if (prev_song < position) {

                    int rr = getResources().getIdentifier(song_url.get(prev_song), "raw", getPackageName());
                    position = prev_song;
                    mp.reset();
                    try {
                        songname.setText(song_url.get(prev_song));
                        mp = MediaPlayer.create(getApplicationContext(), rr);
                        positionBar.setProgress(0);
                        totalTime = mp.getDuration();
                        mp.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                repeat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Reapet) {
                            Reapet = false;
                            repeat.setBackgroundResource(R.drawable.repeatfalse);
                        } else {
                            Reapet = true;
                            repeat.setBackgroundResource(R.drawable.repeattrue);
                        }
                    }
                });
                randomplayer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Randommusic) {
                            Randommusic = false;
                            randomplayer.setBackgroundResource(R.drawable.shufflefalse);
                        } else {
                            Randommusic = true;
                            Reapet = false;
                            randomnumber = rd.nextInt(maxRandomnumber);
                            randomplayer.setBackgroundResource(R.drawable.shuffletrue);
                        }
                    }
                });
                startTime = mp.getCurrentPosition();
                int minutes = ( positionBar.getProgress() % (1000*60*60) ) / (1000*60);
                int seconds = ( ( positionBar.getProgress() % (1000*60*60) ) % (1000*60) ) / 1000;
                finaletime.setText(String.format("%d:%d",minutes,seconds));
                positionBar.setProgress((int)startTime);
                handler.postDelayed(this, 100);
                    try {
                       if(positionBar.getProgress() == positionBar.getMax()){
                           if(Reapet == true){
                               mp.setLooping(true);
                               positionBar.setProgress(0);
                               totalTime = mp.getDuration();
                               mp.start();
                           }else {
                               mp.setLooping(false);
                               mp.reset();
                           }
                           if(Randommusic == true){
                               randomnumber = rd.nextInt(maxRandomnumber);
                               Log.i("SongPlay",randomnumber.toString());
                               int rr = getResources().getIdentifier(song_url.get(randomnumber), "raw", getPackageName());
                               songname.setText(song_url.get(randomnumber));
                               mp = MediaPlayer.create(getApplicationContext(), rr);
                               positionBar.setProgress(0);
                               totalTime = mp.getDuration();
                               positionBar.setMax(totalTime);
                               mp.start();
                           }
                        }
                    } catch (Exception e) {

                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentPosition = msg.what;
            positionBar.setProgress(currentPosition);
        }
    };

}