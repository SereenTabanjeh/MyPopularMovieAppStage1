package com.example.android.mypopularmovieappstage1.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.mypopularmovieappstage1.Models.movie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private final LiveData<List<movie>> favMovies;
    private List<movie> movies;

    public MainViewModel(@NonNull Application application) {
        super(application);

        favMovieDatabase movieDatabase = favMovieDatabase.getInstance(this.getApplication());
        Log.e(TAG, "Actively retrieving the favMovies from the database");
        favMovies = movieDatabase.movieDao().getAllMovies();
    }

    public LiveData<List<movie>> getFavMovies() {
        return favMovies;
    }

    public List<movie> getMovies() {
        return movies;
    }

    public void setMovies(List<movie> movies) {
        this.movies = movies;
    }
}


