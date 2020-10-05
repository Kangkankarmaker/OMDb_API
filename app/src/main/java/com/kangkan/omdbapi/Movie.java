package com.kangkan.omdbapi;

import com.kangkan.omdbapi.model.MovieResponse;
import com.kangkan.omdbapi.model.MovieSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Movie {


    @GET("?")
    Call<MovieResponse> getMovie(

            @Query("t")String title,
            @Query("apikey")String apikey
    );
}

