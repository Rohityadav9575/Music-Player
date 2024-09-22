package com.example.musicplayer;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {
    private final List<MusicFilesModel> musicFilesModels;
    private final Context context;
    public SongsAdapter(List<MusicFilesModel> musicFilesModels, Context context) {
        this.musicFilesModels = musicFilesModels;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view= LayoutInflater.from(context).inflate(R.layout.recycler_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsAdapter.ViewHolder holder, int position) {
        holder.textViewMusicTitle.setText(musicFilesModels.get(position).getTitle());
        byte[] image = null;
        try {
            image = getAlbumArt(musicFilesModels.get(position).getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(image!=null) {
            Glide.with(context)
                    .asBitmap()
                    .load(image)// URL of the image
                    .into(holder.imageViewMusicCover); // Target ImageView
        }else{
            Glide.with(context)
                    .load(R.drawable.default_music)
                    .into(holder.imageViewMusicCover);
        }
    }

    @Override
    public int getItemCount() {
        return musicFilesModels.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewMusicCover;
        TextView textViewMusicTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewMusicCover=itemView.findViewById(R.id.imageViewMusicCover);
            textViewMusicTitle=itemView.findViewById(R.id.textViewMusicTitle);
        }
    }
    private byte[] getAlbumArt(String uri) throws IOException {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(uri);
            return retriever.getEmbeddedPicture();
        } catch (Exception e) {
            // Log the exception instead of throwing it
            Log.e("MediaMetadata", "Error retrieving album art", e);
            return null;
        } finally {
            retriever.release();
        }
    }

}

