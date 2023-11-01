package com.musicstremingapplication;

import static com.musicstremingapplication.HomeFragment.advGupte;
import static com.musicstremingapplication.HomeFragment.listAllSong;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.musicstremingapplication.Models.RecycleSongAdapter;
import com.musicstremingapplication.Models.SongModel;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    DatabaseReference dlist;
    ListView listView;
    ArrayList<String> arrySongs;
    ArrayList<String> arryUrl;

    RecyclerView recyclerView;

    TextView search;
    ImageButton imageButton;

    ArrayList<Songs> song;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        dlist = FirebaseDatabase.getInstance().getReference();
        recyclerView=view.findViewById(R.id.review);


        search=view.findViewById(R.id.searchText);
        imageButton=view.findViewById(R.id.searchButton);

        song=new ArrayList<>();

        RecycleSongAdapter recycleSongAdapter=new RecycleSongAdapter(getContext(),listAllSong);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    song.clear();
                if(!search.getText().toString().isEmpty()){
                    for(int i=0;i<listAllSong.size();i++){
                        if(listAllSong.get(i).getSongName().toLowerCase().contains(search.getText().toString().toLowerCase())){
                            Toast.makeText(getContext(), listAllSong.get(i).getSongName(), Toast.LENGTH_SHORT).show();
                            song.add(listAllSong.get(i));
                        }


                    }
                    RecycleSongAdapter recycleSongAdapter=new RecycleSongAdapter(getContext(),song);
                    recyclerView.setAdapter(recycleSongAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                }
                else {

                    RecycleSongAdapter recycleSongAdapter=new RecycleSongAdapter(getContext(),listAllSong);
                    recyclerView.setAdapter(recycleSongAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                }
            }
        });


//        dlist = FirebaseDatabase.getInstance("https://lisnifymusic-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
//        dlist.child("New").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot songs : snapshot.getChildren()) {
//                    Songs song = songs.getValue(Songs.class);
//                    arrySongs.add(song.getSongName());
//                    arryUrl.add(song.getSongUrl());
//                }
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, arrySongs);
//                listView.setAdapter(arrayAdapter);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
        return view;
    }
}
