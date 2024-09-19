package com.example.musicplayer;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import android.Manifest;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    TabItem songs, album, favourite;
    ViewPager2 viewPager2;
    private static final int REQUEST_CODE = 100;
    private static final String READ_MEDIA= Manifest.permission.READ_MEDIA_AUDIO;// or any unique integer value




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        RunTimePermission();
        intialization();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void intialization() {
        tabLayout = findViewById(R.id.tab_layout);
        songs = findViewById(R.id.tab_item_songs);
        album = findViewById(R.id.tab_item_album);
        favourite = findViewById(R.id.tab_item_favourite);
        viewPager2 = findViewById(R.id.viewpager);



        ViewpagerAdapter viewpagerAdapter=new ViewpagerAdapter(this);
        viewpagerAdapter.addFragment(new AlbumFragment(),"Album");
        viewpagerAdapter.addFragment(new SongsFragment(),"Songs");
        viewpagerAdapter.addFragment(new FavouriteFragment(),"Favourite");
        viewPager2.setAdapter(viewpagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(viewpagerAdapter.getPageTitle(position));
            }
        }).attach();


    }
    public void RunTimePermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_MEDIA_AUDIO) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,READ_MEDIA)) {
            AlertDialog.Builder builder=new AlertDialog.Builder(this)
                    .setMessage("This app require Storage Permission for Music")
                    .setTitle("Permission Required")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{READ_MEDIA}, REQUEST_CODE);
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            ActivityCompat.requestPermissions(this,new String[]{READ_MEDIA}, REQUEST_CODE) ;
        }
    }

}