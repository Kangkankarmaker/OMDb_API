package com.kangkan.omdbapi.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kangkan.omdbapi.MainActivity;
import com.kangkan.omdbapi.MovieItemClickListener;
import com.kangkan.omdbapi.R;
import com.kangkan.omdbapi.model.MovieResponse;
import com.kangkan.omdbapi.model.MovieSearch;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Movie_adapter extends RecyclerView.Adapter<Movie_adapter.MyViewHolder>  {

    Context context;
    List<MovieResponse> mData;
    MovieItemClickListener movieItemClickListener;

    public Movie_adapter(Context context, List<MovieResponse> mData, MovieItemClickListener movieItemClickListener) {
        this.context = context;
        this.mData = mData;
        this.movieItemClickListener = movieItemClickListener;
    }

    @NonNull
    @Override
    public Movie_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Movie_adapter.MyViewHolder holder, int position) {

        holder.title.setText(mData.get(position).getTitle());
        holder.year.setText(mData.get(position).getYear());
        holder.rel.setText(mData.get(position).getType());
        holder.genre.setText(mData.get(position).getImdbID());

        String photoString = mData.get(position).getPoster();
        Uri photoUri = Uri.parse(photoString);
        Picasso.get().load(photoUri).into(holder.poster);

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title,year,rel,genre,language,runtime,director;
        private ImageView poster;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            title=itemView.findViewById(R.id.item_movie_title);
            year=itemView.findViewById(R.id.item_movie_year);
            rel=itemView.findViewById(R.id.item_movie_released);
            genre=itemView.findViewById(R.id.item_movie_Genre);
            language=itemView.findViewById(R.id.item_movie_language);
            runtime=itemView.findViewById(R.id.item_movie_runtime);
            director=itemView.findViewById(R.id.item_movie_director);


            poster=itemView.findViewById(R.id.item_movie_poster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieItemClickListener.onMovieClick(mData.get(getAdapterPosition()));
                }
            });


        }
    }
}
