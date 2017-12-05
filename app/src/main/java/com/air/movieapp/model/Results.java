package com.air.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sagar on 20/8/16.
 */
public class Results {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<Movie> movies;

    public int getPage() {
        return page;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
