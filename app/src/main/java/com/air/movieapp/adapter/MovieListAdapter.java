/*
 *
 *  * Copyright © 2016, Robosoft Technologies Pvt. Ltd
 *  * Written under contract by Robosoft Technologies Pvt. Ltd.
 *
 */

package com.air.movieapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.air.movieapp.R;
import com.air.movieapp.model.Movie;

import java.util.List;

/**
 * Created by sagar on 20/8/16.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private List<Movie> mMovieList;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick();
    }

    public MovieListAdapter(List<Movie> movies) {
        this.mMovieList = movies;
    }

    @Override
    public int getItemCount() {
        return this.mMovieList !=null ? mMovieList.size():0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.view_movie, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, int position) {
        viewHolder.setData(mMovieList.get(position));
    }

    public void setData(List<Movie> data) {
        mMovieList.clear();
        mMovieList.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<Movie> data) {
        mMovieList.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        mMovieList.clear();
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvMovieTitle;
        private TextView mTvReleaseDate;
        private TextView mTvMovieDesc;
        private TextView mTvMovieRating;

        public MovieViewHolder(View view) {
            super(view);
            mTvMovieTitle = (TextView) view.findViewById(R.id.tv_movie_name);
            mTvReleaseDate = (TextView) view.findViewById(R.id.tv_release_date);
            mTvMovieDesc = (TextView) view.findViewById(R.id.tv_movie_desc);
            mTvMovieRating = (TextView) view.findViewById(R.id.tv_rating);
        }

        public void setData(Movie movie){
            mTvMovieTitle.setText(movie.getTitle());
            mTvReleaseDate.setText(movie.getReleaseDate());
            mTvMovieDesc.setText(movie.getOverview());
            mTvMovieRating.setText(""+Math.round(movie.getVoteAverage()));
        }

    }

}

