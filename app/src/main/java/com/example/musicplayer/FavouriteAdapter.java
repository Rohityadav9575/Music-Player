package com.example.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    private List<SongsEntity> favouriteList;
    private Context context;

    public FavouriteAdapter(List<SongsEntity> favouriteList, Context context) {
        this.favouriteList = favouriteList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SongsEntity song = favouriteList.get(position);

        // Set song name and artist
        holder.song_name.setText(song.getName()); // Assuming you have a getTitle() method

        holder.songimage.setImageResource(R.drawable.default_music); // Placeholder image
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public void updateList(List<SongsEntity> newList) {
        this.favouriteList = newList;

        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView songimage;
        TextView song_name, song_artist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songimage = itemView.findViewById(R.id.imageViewMusicCover);
            song_name = itemView.findViewById(R.id.textViewMusicTitle);
            song_artist = itemView.findViewById(R.id.textViewMusicArtist);
        }
    }
}
