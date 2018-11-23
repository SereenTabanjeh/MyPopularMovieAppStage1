package com.example.android.mypopularmovieappstage1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.mypopularmovieappstage1.MainActivity;
import com.example.android.mypopularmovieappstage1.R;

import com.example.android.mypopularmovieappstage1.Models.movie;
import com.squareup.picasso.Picasso;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    String URL_Images = "http://image.tmdb.org/t/p/w185";
    MovieClickListener mMovieClickListener;
    Context mContext;
    movie[] mMovie = null;



    public MovieAdapter(movie[] movies, Context context, MainActivity movieClickListener) {
        mMovie = movies;
        mContext = context;
        mMovieClickListener = (MovieAdapter.MovieClickListener) movieClickListener;
    }

    public interface MovieClickListener {
        void onClickMovie(int position);
    }
    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_layout, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public int getItemCount() {
        return mMovie.length;
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {

            Picasso.get()
                    .load(URL_Images.concat(mMovie[position].getPosterPath()))
                    .fit()
                    .into(holder.imageViewHolder);

    }


    class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView imageViewHolder;


        MovieHolder(View itemView) {
            super(itemView);
            imageViewHolder = itemView.findViewById(R.id.iv_list_item_poster);
            imageViewHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickPosition = getAdapterPosition();
            mMovieClickListener.onClickMovie(clickPosition);
        }
    }


}
