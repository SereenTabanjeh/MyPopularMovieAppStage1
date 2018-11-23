package com.example.android.mypopularmovieappstage1.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.mypopularmovieappstage1.Models.movie;
import com.example.android.mypopularmovieappstage1.database.favMovieDatabase;

import java.util.List;

public class MoiveViewModel  extends ViewModel {


    private LiveData<movie> favMovie;
    private LiveData<List<movie>> favMovieAll;

    public MoiveViewModel(favMovieDatabase database, int taskId) {
        favMovie = database.movieDao().getMovie(taskId);
        favMovieAll = database.movieDao().getAllMovies();
    }


    public LiveData<movie> getMovie() {
        return favMovie;
    }

    public LiveData<List<movie>> getAllMoive() {
        return favMovieAll;
    }
}