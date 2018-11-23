package com.example.android.mypopularmovieappstage1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.mypopularmovieappstage1.Models.Trailer;
import com.example.android.mypopularmovieappstage1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private Context context;
    private final List<Trailer> trailers;

    public TrailerAdapter(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new TrailerViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.trailer_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        final Trailer movieCast = trailers.get(position);

        String imageURL = "http://img.youtube.com/vi/" + movieCast.getKey() + "/mqdefault.jpg";
        Picasso.get()
                .load(imageURL)
                .fit()
                .into(holder.trailerImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("vnd.youtube://" + movieCast.getKey())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {


        final ImageView trailerImage;
        final CardView cardView;

        TrailerViewHolder(View itemView) {
            super(itemView);
            trailerImage = itemView.findViewById(R.id.iv_trailer);
            cardView = itemView.findViewById(R.id.card_view);
            return;
         //   ButterKnife.bind(this, itemView);
        }
    }
}
