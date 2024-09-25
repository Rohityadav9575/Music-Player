package com.example.musicplayer;

import static androidx.core.util.TimeUtils.formatDuration;
import static com.example.musicplayer.MainActivity.musicList;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Random;

public class PlayingActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
ImageView music_image,back;
ImageButton volume,shuffle,Repeat,next,previous;
TextView song_name,current_time,total_time;
SeekBar seekBar;
Button play;
int position=-1;
static Uri uri;
static  ArrayList<MusicFilesModel> listofSongs=new ArrayList<>();
static MediaPlayer mediaPlayer;
Handler handler;
static boolean Shuffled=false,Repeatd=false;
private  Thread PlayThread,NextThread,PrevThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_playing);
        initViews();
        getIntentMethod();
        mediaPlayer.setOnCompletionListener(this);
        handler = new Handler();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);

                }
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        PlayingActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int currenttime = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(currenttime);
                    current_time.setText(formatDuration(currenttime));
                }

                handler.postDelayed(this, 1000);
            }


        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SongsFragment.class);
                startActivity(intent);
            }
        });
        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Shuffled) {
                    shuffle.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white));
                    Log.e("Shuffled", "Shuffled white");// Use ContextCompat to get the actual color value
                    Shuffled = false;
                } else {
                    shuffle.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.blue));
                    Log.e("Shuffled", "Shuffled blue");// Same here
                    Shuffled = true;
                }
            }
        });
        Repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Repeatd){
                    Repeat.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.white));
                    Repeatd=false;
                }else{
                    Repeat.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.blue));
                    Repeatd=true;
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onPostResume() {
        playThreadbtn();
        nextThreadbtn();
        prevThreadbtn();
        super.onPostResume();
    }

    private void prevThreadbtn() {
        PrevThread = new Thread() {
            @Override
            public void run() {
                super.run();
                previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PrevBtnClicked();

                    }
                });
            }
        };
        PrevThread.start();
    }

    private void PrevBtnClicked() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            if(Shuffled && !Repeatd){
                Random random=new Random();
                position=random.nextInt(listofSongs.size());
            } else if (!Shuffled && !Repeatd) {
                position=(position-1)<0?(listofSongs.size()-1):position-1;
            }


            uri=Uri.parse(listofSongs.get(position).getPath());
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            metaData(uri);
            song_name.setText(listofSongs.get(position).getTitle());
            total_time.setText(formatDuration(mediaPlayer.getDuration() / 1000));
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int currenttime=mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(currenttime);
                    }

                    handler.postDelayed(this,1000);
                }


            });
            mediaPlayer.setOnCompletionListener(this);
            play.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_pause, 0, 0);

            mediaPlayer.start();
        }else{
            mediaPlayer.stop();
            mediaPlayer.release();
            if(Shuffled && !Repeatd){
                Random random=new Random();
                position=random.nextInt(listofSongs.size());
            } else if (!Shuffled && !Repeatd) {
                position=(position-1)<0?(listofSongs.size()-1):position-1;
            }


            uri=Uri.parse(listofSongs.get(position).getPath());
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            metaData(uri);
            song_name.setText(listofSongs.get(position).getTitle());
            total_time.setText(formatDuration(mediaPlayer.getDuration() / 1000));
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int currenttime=mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(currenttime);
                    }

                    handler.postDelayed(this,1000);
                }


            });

            play.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_play, 0, 0);

        }
    }

    private void nextThreadbtn() {
        NextThread = new Thread() {
            @Override
            public void run() {
                super.run();
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextBtnClicked();

                    }
                });
            }
        };
        NextThread.start();
        
    }

    private void nextBtnClicked() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            if(Shuffled && !Repeatd){
              Random random=new Random();
              position=random.nextInt(listofSongs.size());
            }else if(!Shuffled && !Repeatd)
            {
            position=(position+1)%listofSongs.size();
            }
            uri = Uri.parse(listofSongs.get(position).getPath());
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            metaData(uri);
            song_name.setText(listofSongs.get(position).getTitle());
            total_time.setText(formatDuration(mediaPlayer.getDuration() / 1000));

            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int currenttime=mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(currenttime);
                    }

                    handler.postDelayed(this,1000);
                }


            });
            mediaPlayer.setOnCompletionListener(this);
            play.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_pause, 0, 0);

            mediaPlayer.start();
        }else{
            mediaPlayer.stop();
            mediaPlayer.release();

            if(Shuffled && !Repeatd){
                Random random=new Random();
                position=random.nextInt(listofSongs.size());
            }else if(!Shuffled && !Repeatd)
            {
                position=(position+1)%listofSongs.size();
            }

            uri=Uri.parse(listofSongs.get(position).getPath());
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            metaData(uri);
            song_name.setText(listofSongs.get(position).getTitle());
            total_time.setText(formatDuration(mediaPlayer.getDuration() / 1000));
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int currenttime=mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(currenttime);
                    }

                    handler.postDelayed(this,1000);
                }


            });

            play.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_play, 0, 0);

        }
    }

    private void playThreadbtn() {
        PlayThread = new Thread() {
            @Override
            public void run() {
                super.run();
                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlayPauseBtnClicked();

                    }
                });
            }
        };
        PlayThread.start();
    }

    private void PlayPauseBtnClicked() {
        if (mediaPlayer.isPlaying()) {
            play.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_play, 0, 0);
            mediaPlayer.pause();
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int currenttime=mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(currenttime);
                    }

                    handler.postDelayed(this,1000);
                }


            });

        }else{
            play.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_pause, 0, 0);
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            PlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int currenttime=mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(currenttime);
                    }

                    handler.postDelayed(this,1000);
                }


            });
        }
    }

    private void getIntentMethod() {
        position=getIntent().getIntExtra("Position",-1);
        listofSongs=musicList;
        if(!listofSongs.isEmpty()){
            // When the song starts playing
           play.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_pause, 0, 0);
            uri=Uri.parse(listofSongs.get(position).getPath());

        }
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
        }else{
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
        }
        song_name.setText(listofSongs.get(position).getTitle());

        total_time.setText(formatDuration(mediaPlayer.getDuration() / 1000));

        seekBar.setMax(mediaPlayer.getDuration()/1000);
        metaData(uri);

    }

    private void metaData(Uri uri) {
        MediaMetadataRetriever mediaMetadataRetriever=new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(uri.toString());
        byte[] art=mediaMetadataRetriever.getEmbeddedPicture();
        if(art!=null){
            Glide.with(this)
                    .asBitmap()
                    .load(art)
                    .into(music_image);
        }else{
            Glide.with(this)
                    .load(R.drawable.default_music)
                    .into(music_image);
        }
    }

    private void initViews() {
        music_image=findViewById(R.id.imageView_music_image);
        back=findViewById(R.id.imageView_back);
        volume=findViewById(R.id.imageButton_volume);
        shuffle=findViewById(R.id.imageButton_shuffle);
        Repeat=findViewById(R.id.imageButton_repeat);
        next=findViewById(R.id.nextButton);
        previous=findViewById(R.id.playButton_previous);
        song_name=findViewById(R.id.song_nametxt);
        current_time=findViewById(R.id.CurrentTime);
        total_time=findViewById(R.id.TotalTime);
        seekBar=findViewById(R.id.musicSeekBar);
        play=findViewById(R.id.play_pauseButton);

    }
    private String formatDuration(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;

        return String.format("%02d:%02d", minutes, remainingSeconds); // Format as MM:SS
    }


    @Override
    public void onCompletion(MediaPlayer mp)   {
        nextBtnClicked();
        if(mediaPlayer!=null){
            mediaPlayer=MediaPlayer.create(this,uri);
            mediaPlayer.start();
            play.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_pause, 0, 0);
            mediaPlayer.setOnCompletionListener(this);
        }
    }
}