package com.example.musicplayer;

import static com.example.musicplayer.MainActivity.musicList;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class SongsFragment extends Fragment {
    RecyclerView songs_recyclerView;
    SongsAdapter songsAdapter;


    public SongsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songs, container, false);
        songs_recyclerView = view.findViewById(R.id.songs_recycleview);
        songs_recyclerView.setHasFixedSize(true);

        // Check if musicList is not null and has items
        if (!musicList.isEmpty()) {
            songsAdapter = new SongsAdapter(musicList, getContext());
            songs_recyclerView.setAdapter(songsAdapter);
            songs_recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        } else {
            // Handle the case where musicList is null or empty
            // For example, show a message or a placeholder view
        }

        return view;
    }


    }
