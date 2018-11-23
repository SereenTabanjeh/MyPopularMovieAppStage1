package com.example.android.mypopularmovieappstage1.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.mypopularmovieappstage1.Models.movie;

import java.util.List;

@Dao
public interface favMovieDao {
    @Query("SELECT * FROM movies")
    LiveData<List<movie>> getAllMovies();

    @Query("SELECT * FROM movies WHERE id = :id")
    LiveData<movie> getMovie(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(movie mMovie);

    @Update
    void updateMovie(movie mMovie);

    @Delete
    void deleteMovie(movie mMovie);
}
