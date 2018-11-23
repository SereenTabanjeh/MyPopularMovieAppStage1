package com.example.android.mypopularmovieappstage1.networking;

import com.example.android.mypopularmovieappstage1.Models.reviewResponse;
import com.example.android.mypopularmovieappstage1.Models.trailerResponse;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RESTClientInterface {

    @GET("movie/{movie_id}/videos")
    Call<trailerResponse> getTrailers(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<reviewResponse> getReviews(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

}