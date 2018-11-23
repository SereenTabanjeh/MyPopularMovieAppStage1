package com.example.android.mypopularmovieappstage1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.mypopularmovieappstage1.Adapter.MoviesFavoriteAdapter;
import com.example.android.mypopularmovieappstage1.Models.movie;
import com.example.android.mypopularmovieappstage1.database.MainViewModel;
import com.example.android.mypopularmovieappstage1.listener.ItemClickListenerObject;

import java.util.List;


public class FavoriteActivity extends AppCompatActivity implements ItemClickListenerObject {

    RecyclerView mFavRecycleview;
    MoviesFavoriteAdapter mAdapter;
    private static final String TAG = "FavoriteActivity";
    Context mContext;
    private TextView tv_error;
    private Button btn_retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setTitle("Favourite Movies");
        mContext = this;
        tv_error = findViewById(R.id.tv_error);
        btn_retry = findViewById(R.id.btn_retry);
        mFavRecycleview = findViewById(R.id.favRecycleview);
        mFavRecycleview.setLayoutManager(new GridLayoutManager(this, 2));
        mFavRecycleview.setHasFixedSize(true);
        mAdapter = new MoviesFavoriteAdapter(this, this);
        setUpViewModelForFavoriteMovie();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void setUpViewModelForFavoriteMovie() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
       try {
           viewModel.getFavMovies().observe(this, new Observer<List<movie>>() {
               @Override
               public void onChanged(@Nullable List<movie> movieFavs) {
                   if (movieFavs != null) {

                       Log.d(TAG, "onChanged: " + movieFavs.toString());
                       mAdapter.setMoviesFavList(movieFavs);
                       mFavRecycleview.setAdapter(mAdapter);
                       mAdapter.notifyDataSetChanged();

                   } else {
                       mFavRecycleview.setVisibility(View.INVISIBLE);
                       tv_error.setText("You Don't have any Favourite Movie");
                       tv_error.setVisibility(View.VISIBLE);
                       return;
                   }
               }
           });
       }
       catch(Exception e){
            e.printStackTrace();
            return;
       }
    }

    @Override
    public void onClickItemObject(int position) {
        movie favMovie = mAdapter.getMoviesFavList().get(position);

        Log.d(TAG, "onClickItemObject: ");
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("title", favMovie.getTitle());
        intent.putExtra("poster", favMovie.getPosterPath());
        intent.putExtra("overview",favMovie.getOverview());
        intent.putExtra("rating", favMovie.getRating());
        intent.putExtra("releaseDate", favMovie.getReleaseDate());
        intent.putExtra("id",favMovie.getId());
        intent.putExtra("movie_details", favMovie);
        startActivity(intent);

          }

    public void clickRetry(View view) {
        if (!isNetworkOnline()) {
            mFavRecycleview.setVisibility(View.INVISIBLE);
            return;
        }

        btn_retry.setVisibility(View.INVISIBLE);
        tv_error.setVisibility(View.INVISIBLE);
        setUpViewModelForFavoriteMovie();
    }

    public boolean isNetworkOnline() {
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                status= true;
            }
            else {
                status = false;
            }

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;

    }
}