package com.example.musicplayer;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    TabItem songs, album, favourite;
    ViewPager2 viewPager2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
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
}