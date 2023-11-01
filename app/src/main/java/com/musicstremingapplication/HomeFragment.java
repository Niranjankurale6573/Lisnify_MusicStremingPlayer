package com.musicstremingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.musicstremingapplication.Adapter.SliderAdapter;
import com.musicstremingapplication.Models.RecycleSongAdapter;
import com.musicstremingapplication.Models.SliderModel;
import com.musicstremingapplication.Models.SongModel;
import com.musicstremingapplication.utils.SliderTiming;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;


public class HomeFragment extends Fragment implements selectListener{
    private ViewPager slider;
    private ArrayList<SliderModel> sliderModelList;
    private SliderAdapter sliderAdapter;
    private TabLayout slider_indicator;
    private Timer timer;

    RecyclerView recyclerView;


    CardView avGupte,honeySingh,ajayAtul,ayushumanKhuranna,sonuNigam,emiwayBantai,gajendraVarma;
    DatabaseReference dlist;

    public static  ArrayList<Songs> listAllSong;
    ArrayList<String>listUrl;
    ArrayList<Songs>listartist;

    public static ArrayList<Songs> advGupte;
    public static  ArrayList<Songs> honeysingh;
    public static ArrayList<Songs>ajay_atul;
    public static ArrayList<Songs>ayushmankhuranna;
    public static ArrayList<Songs>sonunigam;
    public static ArrayList<Songs>emiwaybantai;
    public static ArrayList<Songs>gajendravarma;


    ArrayList<JcAudio> jcAudios;

    JcPlayerView jcPlayerView;

    @Override
    public void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        slider=view.findViewById(R.id.View_slider);
        slider_indicator=view.findViewById(R.id.slider_indicator);
        jcPlayerView=view.findViewById(R.id.jcplayer_control);

        timer=new Timer();

        sliderModelList =new ArrayList<>();

        sliderModelList.add(new SliderModel(R.drawable.kalaastar,"Rap Song"));
        sliderModelList.add(new SliderModel(R.drawable.bhakti_geet,"Bakti Geet"));
        sliderModelList.add(new SliderModel(R.drawable.bhagvatgeeta,"The meaning of life"));
        sliderModelList.add(new SliderModel(R.drawable.hiphop,"Hip-Hop Beat"));
        sliderModelList.add(new SliderModel(R.drawable.party,"Romantic Theam"));
        sliderModelList.add(new SliderModel(R.drawable.wednesday,"Let's Nacho "));

        sliderAdapter=new SliderAdapter(getContext(),sliderModelList);
        slider.setAdapter(sliderAdapter);
        slider_indicator.setupWithViewPager(slider);
        timer.scheduleAtFixedRate(new SliderTiming(getActivity(),slider,sliderModelList.size()),2000,3000);

        //RecycleView
        dlist = FirebaseDatabase.getInstance().getReference();
        recyclerView=view.findViewById(R.id.h_view_song);
        listAllSong=new ArrayList<>();
        listUrl=new ArrayList<>();
        listartist=new ArrayList<>();
        jcAudios = new ArrayList<>();

        //artist array
        advGupte=new ArrayList<>();
        honeysingh=new ArrayList<>();
        ajay_atul=new ArrayList<>();
        ayushmankhuranna=new ArrayList<>();
        sonunigam=new ArrayList<>();
        emiwaybantai=new ArrayList<>();
        gajendravarma=new ArrayList<>();


        dlist = FirebaseDatabase.getInstance("https://lisnifymusic-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

       // ArrayList<ArrayList<Songs>> arr=new ArrayList<>();
        dlist.child("New").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listAllSong.clear();
                for (DataSnapshot songs:snapshot.getChildren()){
                    Songs song=songs.getValue(Songs.class);
                    listAllSong.add(song);
                    listUrl.add(song.getSongUrl());

                    if(song.getSongArtist().contains("Avadhoot Gupte")||song.getSongArtist().contains("avadhoot gupte")||song.getSongArtist().contains("avdhoot gupte")){
                                advGupte.add(song);
                    }
                    if(song.getSongArtist().contains("Yo Yo Honey Singh")||song.getSongArtist().contains("yo yo honey singh")||song.getSongArtist().contains("honey singh")){
                                honeysingh.add(song);
                    }
                    if(song.getSongArtist().contains("ajay atul")||song.getSongArtist().contains("ajay gogavale")||song.getSongArtist().contains("atul gogavale")||song.getSongArtist().contains("Ajay_Atul")||song.getSongArtist().contains("Ajay Atul")){
                                ajay_atul.add(song);
                    }
                    if(song.getSongArtist().contains("Ayushmann Khurrana")||song.getSongArtist().contains("ayushman khurrana")||song.getSongArtist().contains("ayushman_khurrana")){
                                ayushmankhuranna.add(song);
                    }
                    if(song.getSongArtist().contains("sonu nigam")||song.getSongArtist().contains("Sonu Nigam")||song.getSongArtist().contains("Sonu_Nigam")){
                                sonunigam.add(song);
                    }
                    if(song.getSongArtist().contains("Emiway_Bantai")||song.getSongArtist().contains("emiway bantai")||song.getSongArtist().contains("Emiway Bantai")){
                                advGupte.add(song);
                    }
                    if(song.getSongArtist().contains("Gajendra Verma")||song.getSongArtist().contains("gajendra verma")||song.getSongArtist().contains("Gajendra_Verma")){
                                gajendravarma.add(song);
                    }

                       // listartist.add(song.getSongArtist());
                    jcAudios.add(JcAudio.createFromURL(song.getSongName(),song.getSongUrl()));
                }
                //jcPlayerView.initPlaylist(jcAudios,null);
                RecycleSongAdapter recycleSongAdapter=new RecycleSongAdapter(getContext(),listAllSong);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(recycleSongAdapter);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        avGupte=view.findViewById(R.id.avdhutGupte);
        avGupte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ArtistSongs.class);
                intent.putExtra("Name","Avadhoot Gupte");
                intent.putExtra("Pic",R.drawable.avdhoot_gupte);
                startActivity(intent);
            }
        });
        honeySingh=view.findViewById(R.id.honey_singh);
        honeySingh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ArtistSongs.class);
                intent.putExtra("Name","Yo Yo Honey Singh");
                intent.putExtra("Pic",R.drawable.yo_yo);
                startActivity(intent);
            }
        });

        ajayAtul=view.findViewById(R.id.ajay_atul);
        ajayAtul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ArtistSongs.class);
                intent.putExtra("Name","ajay atul");
                intent.putExtra("Pic",R.drawable.ajay_atul);
                startActivity(intent);
            }
        });
        ayushumanKhuranna=view.findViewById(R.id.ayushman_khurran);
        ayushumanKhuranna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ArtistSongs.class);
                intent.putExtra("Name","Ayushmann Khuranna ");
                intent.putExtra("Pic",R.drawable.ayushman);
                startActivity(intent);
            }
        });
        sonuNigam = view.findViewById(R.id.sonu_nigam);
        sonuNigam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ArtistSongs.class);
                intent.putExtra("Name","Sonu Nigam");
                intent.putExtra("Pic",R.drawable.sonu_nigam);
                startActivity(intent);
            }
        });
        emiwayBantai=view.findViewById(R.id.emiway_bantai);
        emiwayBantai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ArtistSongs.class);
                intent.putExtra("Name","Emiway Bantai");
                intent.putExtra("Pic",R.drawable.emiway);
                startActivity(intent);
            }
        });
        gajendraVarma=view.findViewById(R.id.gajendra_verma);
        gajendraVarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ArtistSongs.class);
                intent.putExtra("Name","Gajendra Verma");
                intent.putExtra("Pic",R.drawable.gajendra_verma);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onItemClick(View v, int pos) {
            Toast.makeText(getContext(),listAllSong.get(pos).getSongName()+" selected",Toast.LENGTH_SHORT);
    }
}