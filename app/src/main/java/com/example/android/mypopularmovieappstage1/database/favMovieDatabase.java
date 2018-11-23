package com.example.android.mypopularmovieappstage1.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.mypopularmovieappstage1.Models.movie;

@Database(entities = {movie.class}, version = 4, exportSchema = false)
public abstract class favMovieDatabase extends RoomDatabase {
    private static final String TAG = favMovieDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DB_NAME = "movieDatabase";
    private static volatile favMovieDatabase sInstance;


        public static favMovieDatabase getInstance(Context context) {
            if (sInstance == null) {
                synchronized (LOCK) {
                    Log.d(TAG, "Creating new database instance");
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            favMovieDatabase.class, favMovieDatabase.DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
            Log.d(TAG, "Getting the database instance");
            return sInstance;
        }





    public abstract favMovieDao movieDao();


}
