package com.example.imdb.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imdb.Adapter.MovieResultAdapter;
import com.example.imdb.DataBase.DataBase;
import com.example.imdb.Entity.Result;
import com.example.imdb.R;

import java.util.ArrayList;

public class WatchListActivity extends AppCompatActivity {
    private Result result;
    private RecyclerView watchmovie_list_recyclerview;

    @Override
    protected void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_watch_list);

        watchmovie_list_recyclerview = findViewById(R.id.watchmovie_list_recyclerview);

        this.setTitle("Watchlist");

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            result = bundle.getParcelable("result");

            if(result != null){
                DataBase.getInstance(this).addNewMovie(result.getId(),result.getResultType(),result.getImage(),result.getTitle(),result.getDescription());
            }
        }
        ArrayList<Result> movieArrayList = DataBase.getInstance(this).getMovieList();
        MovieResultAdapter adapter = new MovieResultAdapter(movieArrayList);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getApplicationContext());
        watchmovie_list_recyclerview.setLayoutManager(mlayoutManager);
        watchmovie_list_recyclerview.setAdapter(adapter);

    }

}
