package com.example.android.mypopularmovieappstage1.Models;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class trailerResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<Trailer> trailers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

}
