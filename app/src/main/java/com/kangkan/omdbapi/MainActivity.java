package com.kangkan.omdbapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
    private TextView total_result;
    Movie_adapter adapter;

    List<MovieSearch> respons =new ArrayList<MovieSearch>();
    Movie movie;

    private static final String BASE_URL="http://www.omdbapi.com/";
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
        total_result=findViewById(R.id.total_result);

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
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

        Call<MovieSearch> call =movie.getMovie(mv_name,API_KEY);
        call.enqueue(new Callback<MovieSearch>() {
            @Override
            public void onResponse(Call<MovieSearch> call, Response<MovieSearch> response) {


                respons = Collections.singletonList(response.body());

                List<MovieSearch> movie= Collections.singletonList(response.body());
                for (MovieSearch a : movie)
                {
                    if (a.getResponse().equals("False"))
                    {
                        Toast.makeText(MainActivity.this, "Movie not found!", Toast.LENGTH_LONG).show();
                    }else {
                        List<MovieResponse> search=a.getSearch();
                        adapter=new Movie_adapter(getApplicationContext(), search,MainActivity.this);
                        MovieRV.setAdapter(adapter);
                        MovieRV.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                                LinearLayoutManager.VERTICAL,false));

                        total_result.setText("Total Result :"+a.getTotalResults());
                    }
                }

            }

            @Override
            public void onFailure(Call<MovieSearch> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("error",t.getMessage());
            }
        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @Override
    public void onMovieClick(MovieResponse movie) {

        Intent intent=new Intent(this,MovieDetailsActivity.class);
        intent.putExtra("mv_id",movie.getTitle());
        intent.putExtra("title",movie.getTitle());
        intent.putExtra("image",movie.getPoster());
        intent.putExtra("imageCover",movie.getPoster());
        //intent.putExtra("dics",movie.getPlot());
        intent.putExtra("year",movie.getYear());
        intent.putExtra("type",movie.getType());
        //intent.putExtra("language",movie.getLanguage());
        intent.putExtra("rate",movie.getImdbID());
        //intent.putExtra("cast",movie.getActors());

        startActivity(intent);

    }
}