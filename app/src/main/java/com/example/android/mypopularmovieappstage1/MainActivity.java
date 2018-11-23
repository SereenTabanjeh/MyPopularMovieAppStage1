package com.example.android.mypopularmovieappstage1;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.android.mypopularmovieappstage1.Adapter.MovieAdapter;
import com.example.android.mypopularmovieappstage1.Models.movie;
import com.example.android.mypopularmovieappstage1.database.MainViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieClickListener{


    String myKey = "Your API Key";
    String CALLBACK_QUERY = "callbackQuery";
    String CALLBACK_NAMES = "callbackNames";
    String queryMovie = "popular";
    String title = "Popular Movies";
    movie[] mMovie = null;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    private TextView tv_error;
    private Button btn_retry;
    MovieAdapter movieAdapter ;
    public static List<movie> sFavouriteMovies;
    private MovieAdapter mAdapter;
    private int mCallPage, mCallPagePending, mAdapterPosition = 0;
    private String mSortCategory, mArrangementType;
    private MainViewModel mMainViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        progressBar = findViewById(R.id.pb_main);
        progressBar = findViewById(R.id.pb_main);
        tv_error = findViewById(R.id.tv_error);
        btn_retry = findViewById(R.id.btn_retry);

        if (!isNetworkOnline()) {
            recyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            tv_error.setVisibility(View.VISIBLE);
            btn_retry.setVisibility(View.VISIBLE);
            return;
        }
      setTitle(title);
        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(CALLBACK_QUERY) || savedInstanceState.containsKey(CALLBACK_NAMES)){
                queryMovie = savedInstanceState.getString(CALLBACK_QUERY);
                title = savedInstanceState.getString(CALLBACK_NAMES);
                setTitle(title);
                new  GetAllMovies().execute(queryMovie);
                return;
            }
        }

        new  GetAllMovies().execute(queryMovie);

    }



    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.my_options_menu, menu);

        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (!isNetworkOnline()) {
            recyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            tv_error.setVisibility(View.VISIBLE);
            btn_retry.setVisibility(View.VISIBLE);
            return false;
        }
        int id = item.getItemId();
        if(id== R.id.most_popular) {
            queryMovie = "popular";
            new GetAllMovies().execute(queryMovie);
            setTitle("Most Popular Movies");
        }
         else if(id== R.id.top_rated) {
            queryMovie = "top_rated";
            new GetAllMovies().execute(queryMovie);
            setTitle("Top Rated Movies");
        }
         else if (id == R.id.fav_movie) {
            Intent intent = new Intent(this, FavoriteActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickMovie(int position) {

        if (!isNetworkOnline()) {
            recyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            tv_error.setVisibility(View.VISIBLE);
            btn_retry.setVisibility(View.VISIBLE);
            return;
        }

        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("title", mMovie[position].getTitle());
        intent.putExtra("poster", mMovie[position].getPosterPath());
        intent.putExtra("overview", mMovie[position].getOverview());
        intent.putExtra("rating", mMovie[position].getRating());
        intent.putExtra("releaseDate", mMovie[position].getReleaseDate());
        intent.putExtra("id",mMovie[position].getId());
        intent.putExtra("movie_details", mMovie[position]);
        startActivity(intent);

    }


    public void clickRetry(View view) {
        if (!isNetworkOnline()) {
            recyclerView.setVisibility(View.INVISIBLE);
            return;
        }
        queryMovie = "popular";
        btn_retry.setVisibility(View.INVISIBLE);
        tv_error.setVisibility(View.INVISIBLE);
        new GetAllMovies().execute(queryMovie);
    }

    private void hideProgress() {
      progressBar.setVisibility(View.INVISIBLE);
      tv_error.setVisibility(View.INVISIBLE);
        }


    public class GetAllMovies extends AsyncTask<String, Void, movie[]> {
        protected void onPreExecute() {
            super.onPreExecute();

            recyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
            protected movie[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String moviesJsonStr = null;
            movie movieD[] =null;

            if (myKey.equals("")) {
                tv_error.setText("The api key is Missing !");
                btn_retry.setVisibility(View.INVISIBLE);
                return null;
            }


            try {

                String url_link = "https://api.themoviedb.org/3/movie/";
                Uri uri = Uri.parse(url_link)
                        .buildUpon()
                        .appendPath(params[0])
                        .appendQueryParameter("api_key", myKey)
                        .build();
                URL url = null;
                try {
                    url = new URL(uri.toString());
                } catch (MalformedURLException e) {
                    Log.e( "Problem in creating url", e.getMessage());
                }


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if(inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if(buffer.length() == 0) {
                    return null;
                }

                moviesJsonStr = buffer.toString();

                JSONObject main = new JSONObject(moviesJsonStr);
                JSONArray arr = main.getJSONArray("results");
                movieD = new movie[arr.length()];
                JSONObject movie;
                for(int i =0; i < arr.length(); i++) {
                    movie mov = new movie(0,null,null,null,null,null);
                    movie = arr.getJSONObject(i);
                    mov.setId(movie.getInt("id"));
                    mov.setTitle(movie.optString("title"));
                    mov.setOverview(movie.optString("overview"));
                    mov.setPosterPath(movie.optString("poster_path"));
                    mov.setReleaseDate(movie.optString("release_date"));
                    mov.setRating(movie.optString("vote_average"));

                    movieD[i]= mov;
                }


            } catch (Exception e) {
                Log.e("Exception Appeared : ", e.getMessage());
            }


           return movieD;
        }

        @Override
        protected void onPostExecute(movie[] movies) {
            new GetAllMovies().cancel(true);
            if (movies != null) {
                recyclerView.setVisibility(View.VISIBLE);
                hideProgress();
                mMovie = movies;
                movieAdapter = new MovieAdapter(movies, MainActivity.this,  MainActivity.this);
                recyclerView.setAdapter(movieAdapter);
               movieAdapter.notifyDataSetChanged();
            }

            else {
                Log.e("Error :", "Problems in movie Adapter");
                recyclerView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                tv_error.setText("No Data Available.");
                tv_error.setVisibility(View.VISIBLE);
                btn_retry.setVisibility(View.VISIBLE);
                return;


            }
        }


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



