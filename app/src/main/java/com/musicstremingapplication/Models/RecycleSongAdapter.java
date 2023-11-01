package com.musicstremingapplication.Models;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.musicstremingapplication.MusicPlayMainActivity;
import com.musicstremingapplication.R;
import com.musicstremingapplication.Songs;
import com.musicstremingapplication.selectListener;

import java.io.Serializable;
import java.util.ArrayList;


public class RecycleSongAdapter extends RecyclerView.Adapter<RecycleSongAdapter.ViewHolder> {
    Context context;
    ArrayList<Songs> arrayList;
    private selectListener listener;

    public void setClickListner(selectListener mylistner){
        this.listener=mylistner;

    }

   public RecycleSongAdapter(Context context,ArrayList<Songs> arrayList){
        this.context=context;
        this.arrayList=arrayList;
        this.listener=listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_listview,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        Songs songs=arrayList.get(position);
        holder.songView.setText(songs.getSongName());
        holder.artistView.setText(songs.getSongArtist());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MusicPlayMainActivity.class);
//                intent.putExtra("list",arrayList.toString());
//                intent.putExtra("Name",position);
                Bundle args = new Bundle();
                args.putSerializable("list",arrayList);
                intent.putExtra("BUNDLE",args);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView songView,artistView;
        public ImageView imageView;
        CardView cardView;

        public ViewHolder(View itemView){
            super(itemView);
            imageView=itemView.findViewById(R.id.show_image);
            songView=itemView.findViewById(R.id.song_name);
            artistView=itemView.findViewById(R.id.artist_name);
            cardView=itemView.findViewById(R.id.view_list);

        }
        @Override
        public void onClick(View v) {
            if(listener !=null){
                listener.onItemClick(v,getAdapterPosition());
            }
        }
    }
}
