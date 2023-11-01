package com.musicstremingapplication;

import static android.app.ProgressDialog.*;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Region;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
//import com.musicstremingapplication.Models.ItemClickListner;
import com.musicstremingapplication.databinding.ActivityMainBinding;

import org.checkerframework.common.subtyping.qual.Bottom;

import java.util.ArrayList;
import java.util.UUID;
import java.util.jar.Attributes;

public class Dashboard extends AppCompatActivity {
    private boolean checkPermission=false;
    Uri uri;
    String songName,songUrl;
    String SongTitle,SongArtist,SongtypeStr,Songlang;
    Spinner SpinSongType;
    Animation topAnim;
    ImageView image;
    ImageView showPlayer;


    private FrameLayout fragmentHolder;
    BottomNavigationView navbar;
    ArrayList<String> types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        image=findViewById(R.id.d_icon);
        image.setAnimation(topAnim);
//        showPlayer=findViewById(R.id.show_player);

        if (Build.VERSION.SDK_INT >= LOLLIPOP) {
            //Using Animation
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View, String>(image, "logo_image");
        }
        fragmentHolder = findViewById(R.id.main_frame_layout);
        setFragment(new HomeFragment());

            //Navigation bar
            navbar=findViewById(R.id.BottomNavigationView);
            navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {

                if(item.getItemId()==R.id.menu_home){
                    loadFragment(new HomeFragment());
                    fragmentHolder = findViewById(R.id.main_frame_layout);
                    setFragment(new HomeFragment());

                } else if (item.getItemId()==R.id.menu_search)
                {
                    loadFragment(new SearchFragment());
                    fragmentHolder = findViewById(R.id.main_frame_layout);
                    setFragment(new SearchFragment());
                    Toast.makeText(getApplicationContext(),"Search Music",Toast.LENGTH_SHORT).show();

                } else if (item.getItemId()==R.id.menu_user)
                {
                    loadFragment(new UserFragment());
                    fragmentHolder = findViewById(R.id.main_frame_layout);
                    setFragment(new UserFragment());
                    Toast.makeText(getApplicationContext(),"User Profile ",Toast.LENGTH_SHORT).show();
                } else if (item.getItemId()==R.id.menu_upload)
                {

                    AlertDialog.Builder alert = new AlertDialog.Builder(Dashboard.this);
                    View mView = getLayoutInflater().inflate(R.layout.songalertbox,null);
                    EditText NameSong = mView.findViewById(R.id.song_title);
                    EditText artist = (EditText)mView.findViewById(R.id.name_artist);
                    SpinSongType=mView.findViewById(R.id.songType);
                    EditText language=mView.findViewById(R.id.song_Language);
                    Button btn_okay = (Button)mView.findViewById(R.id.select_file);
                    alert.setView(mView);
                    final AlertDialog alertDialog = alert.create();
                    alertDialog.setCanceledOnTouchOutside(false);


                    //Spinner set
                    types=new ArrayList<>();
                    types.add("Hip-Hop Music");
                    types.add("Rap Music");
                    types.add("Romantic Music");
                    types.add("Bakti Geet");
                    types.add("Dance Music");
                    types.add("Pop Music");
                    types.add("Bhavageete Music");

                    ArrayAdapter<String> Adapter=new ArrayAdapter<>(Dashboard.this, android.R.layout.simple_spinner_item,types);
                    Adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                    SpinSongType.setAdapter(Adapter);

                    SpinSongType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            SongtypeStr=parent.getItemAtPosition(position).toString();
                            //Toast.makeText(Dashboard.this,SongtypeStr+" Type Selected",Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    //Spinner end

                    //btn click listner
                    btn_okay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SongArtist=artist.getText().toString();
                            SongTitle=NameSong.getText().toString();
                            Songlang=language.getText().toString();
                            if(validatePermission()){
                                pickSong();
                            }
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
                return true;
            }
        });
    }
    private void pickSong() {
        Intent intent_upload=new Intent();
        intent_upload.setType("audio/*");
        intent_upload.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent_upload,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                uri=data.getData();
                Cursor mcursor= null;
                mcursor = getApplicationContext().getContentResolver().query( uri,null,null,null);
                int indexedname=mcursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                mcursor.moveToFirst();
                songName=mcursor.getString(indexedname);
                mcursor.close();
                uploadSongToFirebaseStorage();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void uploadSongToFirebaseStorage() {
        StorageReference storageReference= FirebaseStorage.getInstance().getReference()
                .child("AllSongs").child(uri.getLastPathSegment());
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.show();
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri>uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlSong=uriTask.getResult();
                songUrl=urlSong.toString();

                uploadDetailsToDatabase();
                progressDialog.dismiss();
                Toast.makeText(Dashboard.this, songName.toString()+"  Song Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Dashboard.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress=(100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                int currentProgress=(int)progress;
                String message="File Uploading "+currentProgress+"%";
                progressDialog.setMessage(message);
            }
        });
    }

    private void uploadDetailsToDatabase() {
        Songs songObj=new Songs(SongTitle ,songUrl,SongArtist,SongtypeStr,Songlang);
       // Log.d("DEBUG", "abcd :  "+songObj.getSongName() + "  " + songObj.getSongUrl());
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://lisnifymusic-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference df=db.getReference("New").child("Song_".concat(SongTitle));
        df.setValue(songObj);
       // Log.d("DEBUG", "abcd : check db updated");
    }

    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(fragmentHolder.getId(),fragment);
        fragmentTransaction.commit();
    }
    public void loadFragment(Fragment fragment)
    {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.main_frame_layout,new Fragment());
        ft.commit();
    }
    private boolean validatePermission(){
        Dexter.withActivity(Dashboard.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        checkPermission=true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        checkPermission=false;
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
        return checkPermission;

    }

}