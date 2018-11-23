package com.example.android.mypopularmovieappstage1.ViewModels;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Database;

import com.example.android.mypopularmovieappstage1.database.favMovieDatabase;

public class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final favMovieDatabase mDb;
    private final int movie_id;

    public MovieViewModelFactory(int movie_id, favMovieDatabase database) {
        this.movie_id = movie_id;
        this.mDb= database;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MoiveViewModel(mDb, movie_id);
    }
}
