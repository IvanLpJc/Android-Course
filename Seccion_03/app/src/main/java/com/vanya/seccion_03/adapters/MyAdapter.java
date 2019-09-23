package com.vanya.seccion_03.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vanya.seccion_03.R;
import com.vanya.seccion_03.models.Movie;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Movie> movies;
    private int layout ;
    private OnItemClickListener itemClickListener;

    public MyAdapter(List<Movie> movies, int layout, OnItemClickListener listener){
        this.movies = movies;
        this.layout = layout ;
        this.itemClickListener = listener ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(layout,viewGroup,false) ;
        ViewHolder vh = new ViewHolder(v) ;
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(movies.get(i), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName ;
        public ImageView imageViewPoster;

        public ViewHolder(View v){
            super(v) ;
            textViewName = v.findViewById(R.id.textViewTitle) ;
            imageViewPoster = v.findViewById(R.id.imageViewPoster) ;
        }

        public void bind(final Movie movie, final OnItemClickListener listener){

            this.textViewName.setText(movie.getName());
            //this.imageViewPoster.setImageResource(movie.getPoster());

            Picasso.get().load(movie.getPoster()).fit().into(imageViewPoster); ;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(movie, getAdapterPosition());
                }
            });

        }
    }

    public interface OnItemClickListener{
        void onItemClick(Movie movie, int position) ;
    }
}
