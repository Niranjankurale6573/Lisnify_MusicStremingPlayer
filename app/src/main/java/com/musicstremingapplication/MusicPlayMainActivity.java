package com.musicstremingapplication;

import static com.musicstremingapplication.HomeFragment.listAllSong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionBarPolicy;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MusicPlayMainActivity extends AppCompatActivity {
    TextView songTitle,artistName;
    ImageView musicIcon;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play_main);

        songTitle = findViewById(R.id.song_title);
        artistName = findViewById(R.id.artist_name);
        musicIcon = findViewById(R.id.song_image);
        JcPlayerView jcPlayerView = findViewById(R.id.jcplayer);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<Songs> songsPlaylist = (ArrayList<Songs>) args.getSerializable("list");
        Log.d("TAG", "onCreate: "+ songsPlaylist.toString());


        ArrayList<JcAudio> jcAudios = new ArrayList<>();
        songsPlaylist.forEach(song -> jcAudios.add(JcAudio.createFromURL(song.getSongName(),song.getSongUrl())));

        jcPlayerView.initPlaylist(jcAudios,null);
    }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}