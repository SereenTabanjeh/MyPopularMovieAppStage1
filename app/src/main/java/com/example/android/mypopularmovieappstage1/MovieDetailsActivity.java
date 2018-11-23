package com.example.android.mypopularmovieappstage1;

import android.arch.lifecycle.Observer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mypopularmovieappstage1.Adapter.ReviewAdapter;
import com.example.android.mypopularmovieappstage1.Adapter.TrailerAdapter;
import com.example.android.mypopularmovieappstage1.Models.Review;
import com.example.android.mypopularmovieappstage1.Models.Trailer;
import com.example.android.mypopularmovieappstage1.Models.movie;
import com.example.android.mypopularmovieappstage1.Models.reviewResponse;
import com.example.android.mypopularmovieappstage1.Models.trailerResponse;
import com.example.android.mypopularmovieappstage1.database.favMovieDatabase;
import com.example.android.mypopularmovieappstage1.networking.RESTClient;
import com.example.android.mypopularmovieappstage1.networking.RESTClientInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieDetailsActivity extends AppCompatActivity {

    String img_path = "http://image.tmdb.org/t/p/w185";
    ArrayList<Trailer> mTrailerList;
    TrailerAdapter mTrailerAdapter;
   String myKey = "91553b2aef4dee5b9438fdbb72220295";
    ImageView imageView ;
    TextView overview ;
    TextView releaseDate;
    TextView rating ;
    RecyclerView trailerRecyclerView;
    LinearLayout trailerLayout  ;
    RecyclerView reviewRecyclerView;
    LinearLayout reviewLayout;
    private static Retrofit retrofit = null;
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    ImageView details_fav;
    movie thisMovie;
    private String ACTION = "INSERT";
    private String INSERT_ACTION = "INSERT";
    private String DELETE_ACTION = "DELETE";
    private final String TAG = "MovieDetailsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        imageView = findViewById(R.id.iv_img_poster);
        overview = findViewById(R.id.tv_overview);
        releaseDate = findViewById(R.id.tv_release);
        rating = findViewById(R.id.tv_rating);
        trailerRecyclerView = findViewById(R.id.rv_trailer);
        trailerLayout =findViewById(R.id.ll_trailers);
        reviewRecyclerView = findViewById(R.id.rv_review);
        reviewLayout = findViewById(R.id.ll_reviews);
        details_fav = findViewById(R.id.details_fav);


        final movie myMovie = (movie) getIntent().getParcelableExtra("movie_details");

        String intent_title = getIntent().getStringExtra("title");
        String intent_poster = getIntent().getStringExtra("poster");
        String intent_overview = getIntent().getStringExtra("overview");
        String intent_rating = getIntent().getStringExtra("rating");
        String intent_release = getIntent().getStringExtra("releaseDate");
        int id = getIntent().getIntExtra("id", 0);

        setTitle(intent_title);
        overview.setText(intent_overview);
        releaseDate.setText(intent_release);
        rating.setText(intent_rating);


        Picasso.get().load(img_path.concat(intent_poster)).into(imageView);

        FetchTrailers(id);
        FetchReviews(id);
        checkMovie(id);

        details_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToFavorite(myMovie);
            }
        });
    }

    private void FetchTrailers(int movieId) {
        RESTClientInterface restClientInterface = RESTClient.getClient().create(RESTClientInterface.class);
        Call<trailerResponse> response = restClientInterface.getTrailers(movieId,myKey);

        if (response != null) {
            response.enqueue(new retrofit2.Callback<trailerResponse>() {
                @Override
                public void onResponse(@NonNull Call<trailerResponse> call,
                                       @NonNull Response<trailerResponse> response) {
                    int statusCode = response.code();

                    if (statusCode == 200) {
                        if (response.body() != null) {
                            trailerResponse movieTrailerResponse = response.body();
                            List<Trailer> trailers = movieTrailerResponse != null ? movieTrailerResponse.getTrailers() : null;

                            if (trailers != null && trailers.size() > 0) {
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MovieDetailsActivity.this,
                                        LinearLayoutManager.HORIZONTAL,
                                        false);

                                trailerRecyclerView.setLayoutManager(layoutManager);
                                trailerRecyclerView.setHasFixedSize(true);
                                trailerRecyclerView.setAdapter(new TrailerAdapter(trailers));
                            } else {
                                trailerLayout.setVisibility(View.GONE);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<trailerResponse> call, @NonNull Throwable throwable) {
                    // Log error here since request failed
                    Log.e("Errorrrrr", throwable.toString());
                }
            });
        }
    }
    private void FetchReviews(int movieId){
        RESTClientInterface restClientInterface = RESTClient.getClient().create(RESTClientInterface.class);
        Call<reviewResponse> response = restClientInterface.getReviews(movieId,myKey);

        if (response != null) {
            response.enqueue(new retrofit2.Callback<reviewResponse>() {
                @Override
                public void onResponse(@NonNull Call<reviewResponse> call,
                                       @NonNull Response<reviewResponse> response) {
                    int statusCode = response.code();

                    if (statusCode == 200) {
                        if (response.body() != null) {
                            reviewResponse movieReviewResponse = response.body();
                            List<Review> reviews = movieReviewResponse != null ? movieReviewResponse.getResults() : null;

                            if (reviews != null && reviews.size() > 0) {
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MovieDetailsActivity.this,
                                        LinearLayoutManager.VERTICAL,
                                        false);

                                reviewRecyclerView.setLayoutManager(layoutManager);
                                reviewRecyclerView.setHasFixedSize(true);
                                reviewRecyclerView.setAdapter(new ReviewAdapter(reviews));
                            } else {
                                reviewLayout.setVisibility(View.GONE);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<reviewResponse> call, @NonNull Throwable throwable) {
                    // Log error here since request failed
                    Log.e("errror", throwable.toString());
                }
            });
        }
    }

    private void saveToFavorite(movie myMovie) {



        String title = myMovie.getTitle();
        int id = myMovie.getId();
        String pathImage = myMovie.getPosterPath();
        String overview = myMovie.getOverview();
        String voteAvarage = myMovie.getRating();
        String releaseDate = myMovie.getReleaseDate();
         final movie  movieFav = new movie(id, title, pathImage, overview, voteAvarage, releaseDate);
         final String insertMessage = "Added to favourites";
         final String deleteMessage = "Removed from favourites";

        if (ACTION.equals(INSERT_ACTION)) {

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    try {

                        favMovieDatabase.getInstance(getApplicationContext()).movieDao().insertMovie(movieFav);
                        Log.d(TAG, "run: insert done !");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), insertMessage, Toast.LENGTH_SHORT).show();
                                details_fav.setImageResource(R.drawable.ic_favorite_red_26dp);
                                ACTION=DELETE_ACTION;

                            }
                        });
                    }
                    catch (Exception  e) {
                        Log.e(TAG, e.getMessage());
                    }



                }
            });

        }
        if (ACTION.equals(DELETE_ACTION)) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                    favMovieDatabase.getInstance(getApplicationContext()).movieDao().deleteMovie(movieFav);
                    Log.d(TAG, "run: delete done !");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), deleteMessage, Toast.LENGTH_SHORT).show();
                            details_fav.setImageResource(R.drawable.ic_favorite_border_red_26dp);
                           ACTION=INSERT_ACTION;
                        }
                    });

                    }
                    catch (Exception  e) {
                        Log.e(TAG, e.getMessage());
                    }

                }
            });


        }

    }


    private void checkMovie(int movieId) {
        favMovieDatabase.getInstance(getApplicationContext()).movieDao().getMovie(movieId)
                        .observe(this, new Observer<movie>() {
                            @Override
                                    public void onChanged(@Nullable movie favMovieEntities) {
                                        if (favMovieEntities != null ) {
                                            //that mean the movie is here and we need to delete it BUT SHOW THTE ICON
                                            ACTION = DELETE_ACTION;
                                            details_fav.setImageResource(R.drawable.ic_favorite_red_26dp);
                                        } else {
                                            Log.d(TAG, "editFavImage: Empty");
                                            ACTION = INSERT_ACTION;
                                            details_fav.setImageResource(R.drawable.ic_favorite_border_red_26dp);

                                        }
                                    }
                                });




    }
}
