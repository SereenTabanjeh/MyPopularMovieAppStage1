package com.example.android.mypopularmovieappstage1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mypopularmovieappstage1.Models.Review;
import com.example.android.mypopularmovieappstage1.Models.Trailer;
import com.example.android.mypopularmovieappstage1.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context context;
    private final List<Review> reviews;

    public ReviewAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new ReviewViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.review_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        Review review = reviews.get(position);
        holder.auther.setText(review.getAuthor());
        holder.reviewContent.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        final TextView auther;
        final TextView reviewContent;

        ReviewViewHolder(View itemView) {
            super(itemView);
            auther = itemView.findViewById(R.id.tv_auther);
            reviewContent = itemView.findViewById(R.id.tv_reviewContent);
            return;

        }
    }
}
