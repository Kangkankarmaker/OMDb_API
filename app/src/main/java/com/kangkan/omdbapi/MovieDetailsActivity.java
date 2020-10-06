package com.kangkan.omdbapi;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    ImageView movieImg,movieCoverimg;
    TextView title,titleDec,year,language,type,ret_person,mv_cast;
    private FloatingActionButton actionButton;
    private RatingBar rBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        init();

        setUpIntent();
    }

    private void init() {
        movieImg=findViewById(R.id.details_movie_img);
        movieCoverimg=findViewById(R.id.details_movie_cover);
        ret_person=findViewById(R.id.ret_person);
        title=findViewById(R.id.details_movie_title);
        titleDec=findViewById(R.id.details_movie_dec);
        year=findViewById(R.id.details_movie_year);
        type=findViewById(R.id.details_movie_type);
        language=findViewById(R.id.details_movie_lang);
        actionButton=findViewById(R.id.details_movie_fab);
        mv_cast=findViewById(R.id.mv_cast);
        rBar=findViewById(R.id.ratingBar);

    }

    private void setUpIntent() {


        String movieid=getIntent().getExtras().getString("mv_id");

        String movieTitle=getIntent().getExtras().getString("title");
        String imageRec=getIntent().getExtras().getString("image");
        String imageRecCover=getIntent().getExtras().getString("imageCover");
        String movieDec=getIntent().getExtras().getString("dics");
        String movieYear=getIntent().getExtras().getString("year");
        String movieType=getIntent().getExtras().getString("type");
        String movieLanguage=getIntent().getExtras().getString("language");
        String movieRate=getIntent().getExtras().getString("rate");
        String moviecast=getIntent().getExtras().getString("cast");

        Uri poster = Uri.parse(imageRec);

        try {
            Uri coverImg = Uri.parse(imageRecCover);
            Picasso.get().load(poster).into(movieImg);
            Picasso.get().load(coverImg).into(movieCoverimg);

            title.setText(movieTitle);
            titleDec.setText(movieDec);
            year.setText(movieYear);
            language.setText(movieLanguage);
            type.setText(movieType);
            ret_person.setText(movieRate);
            mv_cast.setText(moviecast);


            assert movieRate != null;
            rBar.setRating(Float.parseFloat(movieRate));
        }catch (Exception e){}
    }
}