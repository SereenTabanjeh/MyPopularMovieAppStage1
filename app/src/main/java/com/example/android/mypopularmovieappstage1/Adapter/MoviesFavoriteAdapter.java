package com.example.android.mypopularmovieappstage1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.mypopularmovieappstage1.Models.movie;
import com.example.android.mypopularmovieappstage1.R;
import com.example.android.mypopularmovieappstage1.listener.ItemClickListenerObject;
import com.squareup.picasso.Picasso;

import java.util.List;



public class MoviesFavoriteAdapter extends RecyclerView.Adapter<MoviesFavoriteAdapter.MovieFavViewHolder>{

    private static final String TAG = "MoviesFavoriteAdapter";
    ItemClickListenerObject mItemClickLitenerObject;
    public static final String BASE_IMAGE_URL="http://image.tmdb.org/t/p/w185/";
    Context mContext;


    List<movie> moviesFavList;

    public MoviesFavoriteAdapter(Context context , ItemClickListenerObject mItemClickLitenerObject) {
        this.mItemClickLitenerObject=mItemClickLitenerObject;
        this.mContext = context;
        notifyDataSetChanged();
     }

    public void setMoviesFavList(List<movie> moviesFavList) {
        this.moviesFavList = moviesFavList;
    }


    @Override
    public MovieFavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new MovieFavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieFavViewHolder holder, int position) {


        String pathImage=moviesFavList.get(position).getPosterPath();


         String fullPath= BASE_IMAGE_URL+pathImage;

        Picasso.get()
                .load(fullPath)
                .fit()
                .error(R.drawable.ic_play_arrow_black_24dp)
                .into(holder.imageViewPoster);

    }

    @Override
    public int getItemCount() {
        return moviesFavList.size();
    }

    public List<movie> getMoviesFavList() {
        return moviesFavList;
    }



    public class MovieFavViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageViewPoster ;




        public MovieFavViewHolder(View itemView) {
            super(itemView);
            imageViewPoster =itemView.findViewById(R.id.menu_image_Poster);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            mItemClickLitenerObject.onClickItemObject(getAdapterPosition());
        }
    }
}
