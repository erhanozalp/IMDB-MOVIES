package com.example.imdb.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.imdb.R;

public class MainActivity extends Activity {

    private EditText search_movie_Edittext;
    private Button Show_watchlist_btn;
    private Button Show_searched_movie_btn;
    private String movieName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_movie_Edittext = findViewById(R.id.search_movie_Edittext);
        Show_watchlist_btn = findViewById(R.id.Show_watchlist_btn);
        Show_searched_movie_btn = findViewById(R.id.Show_searched_movie_btn);

        Show_watchlist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateWatchListActivity();
            }
        });

        Show_searched_movie_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMovieNameToLocalDB(search_movie_Edittext.getText().toString());
                navigateRecordedMovieDetailActivity();
            }
        });

        movieName = getMovieNameFromLocalDB();

    }

    private void saveMovieNameToLocalDB(String movieName){
        //Validation

        this.movieName = movieName;

        //Local db'ye film isimlerini ekle
        String CONST_DATA = "MOVIE_NAME";
        SharedPreferences preferences = this.getSharedPreferences(CONST_DATA, getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CONST_DATA, String.valueOf(movieName));
        editor.apply();
    }

    private String getMovieNameFromLocalDB(){
        String result;
        String CONST_DATA = "MOVIE_NAME";
        SharedPreferences preferences = this.getSharedPreferences(CONST_DATA, getApplicationContext().MODE_PRIVATE);
        result = preferences.getString(CONST_DATA, "");

        return result;
    }

    private void navigateRecordedMovieDetailActivity() {
        Intent RecordedMovieIntent = new Intent(MainActivity.this, MovieListActivity.class);
        RecordedMovieIntent.putExtra("movie_name",movieName);
        startActivity(RecordedMovieIntent);
    }

    private void navigateWatchListActivity() {
        if(movieName != "" && movieName != " "){
            Intent MovieListActivityIntent = new Intent(MainActivity.this, WatchListActivity.class);
            MovieListActivityIntent.putExtra("movie_name", movieName);
            startActivity(MovieListActivityIntent);
        }

    }

    //16.dk


}