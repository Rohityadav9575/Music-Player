package com.example.musicplayer;

import android.os.Bundle;


import androidx.fragment.app.Fragment;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class FavouriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<SongsEntity> songsEntities;
    private RecyclerView.Adapter adapter;


    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_favourite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        songsEntities = new ArrayList<>();

        // adapter intialization
        adapter = new FavouriteAdapter(songsEntities, getContext());
        recyclerView.setAdapter(adapter);
        loadFavouriteSongs();

        return view;
    }


    public void loadFavouriteSongs() {
        AppDatabase appDatabase = AppDatabase.getInstance(getContext());
        appDatabase.dao().getAllSongs().observe(getViewLifecycleOwner(), fetchSongs -> {
            songsEntities.clear();
            songsEntities.addAll(fetchSongs);
            adapter.notifyDataSetChanged();
        });
    }
}