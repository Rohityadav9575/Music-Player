package com.example.musicplayer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import android.Manifest;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 100;
    private static final String READ_MEDIA = Manifest.permission.READ_MEDIA_AUDIO ;
    static ArrayList<MusicFilesModel> musicList;

   // private RecyclerView songs_recyclerView;
    //private SongsAdapter songsAdapter;
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        RunTimePermission();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initialize() {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.viewpager);

        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(this);
        viewpagerAdapter.addFragment(new AlbumFragment(), "Albums");
        viewpagerAdapter.addFragment(new SongsFragment(), "Songs");
        viewpagerAdapter.addFragment(new FavouriteFragment(), "Favourite");

        viewPager2.setAdapter(viewpagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) ->
                tab.setText(viewpagerAdapter.getPageTitle(position))
        ).attach();
    }

    private void RunTimePermission() {
        if (ContextCompat.checkSelfPermission(this, READ_MEDIA) == PackageManager.PERMISSION_GRANTED) {
            musicList = getAllFiles(this);
           // Log.v("FetchingMusic","Fetching files in run");

           initialize();
           //setupRecyclerView();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{READ_MEDIA}, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // IF PERMISSION GRANTED
        if (requestCode == REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            musicList = getAllFiles(this);
           //Log.v("FetchingMusic","Fetching files in onrun");

           initialize();
           //setupRecyclerView();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, READ_MEDIA)) {
                // ASK FOR PERMISSION AGAIN IF NOT PERMANENTLY DENIED
                ActivityCompat.requestPermissions(this, new String[]{READ_MEDIA}, REQUEST_CODE);
               // musicList=getAllFiles(this);
            } else {
                // PERMISSION PERMANENTLY DENIED, SHOW A MESSAGE OR GUIDE USER TO SETTINGS
                Toast.makeText(this, "Permission denied permanently. Enable it from settings.", Toast.LENGTH_LONG).show();
                // Optionally, guide the user to app settings
            }
        }
    }

    public static ArrayList<MusicFilesModel> getAllFiles(Context context) {
        ArrayList<MusicFilesModel> tempMusicList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA, //FOR DATA
                MediaStore.Audio.Media.ARTIST
        };

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        Log.v("Cursor","Cursor created");
            if (cursor != null) {
               // Log.v("if","inside if");
                while (cursor.moveToNext()) {


                    String title = cursor.getString(0); // title
                    String album = cursor.getString(1);// path
                    String duration = cursor.getString(2); // duration
                    String path = cursor.getString(3); // path
                    String artist = cursor.getString(4); // artist
                    MusicFilesModel musicFilesModel=new MusicFilesModel(path,title,duration,artist,album);

                    Log.e("Path",path);
                    tempMusicList.add(musicFilesModel);
                }
                cursor.close();
            } else {
                Log.v("NoFiles", "No files found");
            }

        return tempMusicList;
    }
    // ERROR HERE
   /* private void setupRecyclerView() {
        if (musicList != null && !musicList.isEmpty()) {
            songsAdapter = new SongsAdapter(musicList, this);
            songs_recyclerView.setAdapter(songsAdapter);
            songs_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            Log.v("MusicFiles", "No music files available.");
        }
    }*/

}
