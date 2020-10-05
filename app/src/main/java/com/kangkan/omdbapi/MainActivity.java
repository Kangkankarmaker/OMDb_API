package com.kangkan.omdbapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kangkan.omdbapi.adapter.Movie_adapter;
import com.kangkan.omdbapi.model.MovieResponse;
import com.kangkan.omdbapi.model.MovieSearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MovieItemClickListener{

    private RecyclerView MovieRV;
    private EditText editText;
    private Button button_search;
    Movie_adapter adapter;

    List<MovieResponse> respons =new ArrayList<MovieResponse>();
    Movie movie;

    private static final String BASE_URL="http://www.omdbapi.com/?t=";
    private static final String API_KEY="473ebeb0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        MovieRV=findViewById(R.id.rv_movie);
        editText=findViewById(R.id.ext_movie_name);
        button_search=findViewById(R.id.btn_search_movie);

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mv_name=editText.getText().toString();
                if (mv_name .isEmpty()) {
                   Toast.makeText(MainActivity.this, "Enter a Movie Name", Toast.LENGTH_SHORT).show();
                }else {
                    searchMovie(mv_name);
                }
            }
        });
    }

    private void searchMovie(String mv_name) {


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL )
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        movie = retrofit.create(Movie.class);

        Call<MovieResponse> call =movie.getMovie(mv_name,API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {


                respons = Collections.singletonList(response.body());

                List<MovieResponse> movieResponse= Collections.singletonList(response.body());
                for (MovieResponse a : movieResponse)
                {
                    if (a.getResponse().equals("False"))
                    {
                        Toast.makeText(MainActivity.this, "Movie not found!", Toast.LENGTH_LONG).show();
                    }else {
                        adapter=new Movie_adapter(getApplicationContext(), respons,MainActivity.this);
                        MovieRV.setAdapter(adapter);
                        MovieRV.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                                LinearLayoutManager.VERTICAL,false));
                    }
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("error",t.getMessage());
            }
        });
    }


    @Override
    public void onMovieClick(MovieResponse movie) {

        Intent intent=new Intent(this,MovieDetailsActivity.class);
        intent.putExtra("mv_id",movie.getDVD());
        intent.putExtra("title",movie.getTitle());
        intent.putExtra("image",movie.getPoster());
        intent.putExtra("imageCover",movie.getPoster());
        intent.putExtra("dics",movie.getPlot());
        intent.putExtra("year",movie.getYear());
        intent.putExtra("type",movie.getType());
        intent.putExtra("language",movie.getLanguage());
        intent.putExtra("rate",movie.getImdbRating());
        intent.putExtra("cast",movie.getActors());

        startActivity(intent);

    }
}