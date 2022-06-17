package com.example.imdb.Activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.imdb.Adapter.MovieResultAdapter;
import com.example.imdb.Entity.MovieResult;
import com.example.imdb.Entity.Result;
import com.example.imdb.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MovieListActivity extends AppCompatActivity {

    private String movieName;
    private RecyclerView movie_list_recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        movie_list_recyclerview = findViewById(R.id.movie_list_recyclerview);
        this.setTitle("MOVIES");

        Bundle bundle = getIntent().getExtras();

        movieName = "";

        if(bundle != null){
            movieName = bundle.getString("movie_name");
        }

        getMovieListFromNetwork(movieName);



    }

    private void getMovieListFromNetwork(String movieName){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://imdb-api.com/API/SearchMovie/k_dfck1qfc/"+movieName)
                .method("GET", null)
                .addHeader("accept", "text/plain")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    final String responseBody = response.body().string();

                    MovieResult movieResult = new Gson().fromJson(responseBody, MovieResult.class);

                    MovieListActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setAdapterRecyclerView(movieResult.getResults());
                        }
                    });


                    Log.d(TAG, "onResponse: ");


                }
            }
        });
    }

    private void setAdapterRecyclerView(List<Result> resultList){
        MovieResultAdapter adapter = new MovieResultAdapter(resultList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        movie_list_recyclerview.setLayoutManager(mLayoutManager);
        movie_list_recyclerview.setAdapter(adapter);

    }


}