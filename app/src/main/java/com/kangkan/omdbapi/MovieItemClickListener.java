package com.kangkan.omdbapi;

import android.widget.ImageView;

import com.kangkan.omdbapi.model.MovieResponse;
import com.kangkan.omdbapi.model.MovieSearch;

public interface MovieItemClickListener {
    void onMovieClick(MovieResponse movie);

}

