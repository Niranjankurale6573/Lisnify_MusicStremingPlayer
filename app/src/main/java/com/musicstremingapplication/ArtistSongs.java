package com.musicstremingapplication;

import static com.musicstremingapplication.HomeFragment.advGupte;
import static com.musicstremingapplication.HomeFragment.ajay_atul;
import static com.musicstremingapplication.HomeFragment.ayushmankhuranna;
import static com.musicstremingapplication.HomeFragment.emiwaybantai;
import static com.musicstremingapplication.HomeFragment.gajendravarma;
import static com.musicstremingapplication.HomeFragment.honeysingh;
import static com.musicstremingapplication.HomeFragment.sonunigam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.musicstremingapplication.Models.RecycleSongAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArtistSongs extends AppCompatActivity {


    CircleImageView circleImageView;
    RecyclerView recyclerView;
    TextView artistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_songs);

            recyclerView = findViewById(R.id.recylerview);
            circleImageView = findViewById(R.id.artist_images);
            artistName = findViewById(R.id.artist_heading);


            Intent intent = getIntent();
            circleImageView.setImageResource(intent.getIntExtra("Pic", R.drawable.avdhoot_gupte));
            artistName.setText(intent.getStringExtra("Name"));

            if (intent.getStringExtra("Name").equals("Avadhoot Gupte")) {
                try {
                    RecycleSongAdapter recycleSongAdapter = new RecycleSongAdapter(this, advGupte);
                    recyclerView.setAdapter(recycleSongAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error Found ", Toast.LENGTH_SHORT).show();
                }

            }

            if (intent.getStringExtra("Name").equals("Yo Yo Honey Singh")) {
                RecycleSongAdapter recycleSongAdapter = new RecycleSongAdapter(this, honeysingh);
                recyclerView.setAdapter(recycleSongAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
            if (intent.getStringExtra("Name").equals("Ajay Atul")) {
                RecycleSongAdapter recycleSongAdapter = new RecycleSongAdapter(this, ajay_atul);
                recyclerView.setAdapter(recycleSongAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
            if (intent.getStringExtra("Name").equals("Ayushmann Khuranna")) {
                RecycleSongAdapter recycleSongAdapter = new RecycleSongAdapter(this, ayushmankhuranna);
                recyclerView.setAdapter(recycleSongAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
            if (intent.getStringExtra("Name").equals("Sonu Nigam")) {
                RecycleSongAdapter recycleSongAdapter = new RecycleSongAdapter(this, sonunigam);
                recyclerView.setAdapter(recycleSongAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
            if (intent.getStringExtra("Name").equals("Emiway Bantai")) {
                RecycleSongAdapter recycleSongAdapter = new RecycleSongAdapter(this, emiwaybantai);
                recyclerView.setAdapter(recycleSongAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
            if (intent.getStringExtra("Name").equals("Gajendra Verma")) {
                RecycleSongAdapter recycleSongAdapter = new RecycleSongAdapter(this, gajendravarma);
                recyclerView.setAdapter(recycleSongAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }

        }
}