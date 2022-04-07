package com.example.audiodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.audiodemo.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    List<Integer> song;
    List<Integer> image;
    int songSelect = 0;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        image = new ArrayList<Integer>(Arrays.asList(R.drawable.emerald,R.drawable.platinum,R.drawable.heartgold));
        song = new ArrayList<Integer>(Arrays.asList(R.raw.emerald,R.raw.platinum,R.raw.heartgold));
        mediaPlayer = MediaPlayer.create(this, song.get(songSelect));
        binding.imageViewPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying())
                {
                    binding.imageViewPlayPause.setImageResource(android.R.drawable.ic_media_play);
                    mediaPlayer.pause();
                }
                else
                {
                    binding.imageViewPlayPause.setImageResource(android.R.drawable.ic_media_pause);
                    mediaPlayer.start();
                }
            }
        });
        binding.imageViewStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying())
                    binding.imageViewPlayPause.setImageResource(android.R.drawable.ic_media_play);
                else
                    binding.imageViewPlayPause.setImageResource(android.R.drawable.ic_media_pause);
                mediaPlayer.stop();
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(songSelect!=2)
                    songSelect++;
                else
                    songSelect=0;
                changeSong(songSelect,"Yes");
            }
        });
        binding.prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(songSelect!=0)
                    songSelect--;
                else
                    songSelect=2;
               changeSong(songSelect,"Yes");
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();
                if(songSelect!=2)
                {
                    songSelect++;
                    changeSong(songSelect,"Yes");
                }
                else
                {
                    songSelect=0;
                    changeSong(songSelect,"No");
                }
            }
        });
        binding.forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = mediaPlayer.getCurrentPosition()+15000;
                int duration = mediaPlayer.getDuration();
                if(position<duration)
                    mediaPlayer.seekTo(position);
                else
                    mediaPlayer.seekTo(duration);

            }
        });
        binding.reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = mediaPlayer.getCurrentPosition()-15000;
                int duration = mediaPlayer.getDuration();
                if(position>0)
                    mediaPlayer.seekTo(position);
                else
                    mediaPlayer.seekTo(0);
            }
        });


    }
    public void changeSong(int songSelected,String fin)
    {
        mediaPlayer.stop();
        mediaPlayer = MediaPlayer.create(MainActivity.this, song.get(songSelected));
        binding.imageView.setImageResource(image.get(songSelected));
        if(fin.equalsIgnoreCase("No"))
            mediaPlayer.start();
    }
}