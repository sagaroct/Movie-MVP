package com.air.movieapp.ui.movielist;

import android.support.v7.util.DiffUtil.Callback;
import android.text.TextUtils;

import com.air.movieapp.data.model.Movie;
import com.air.movieapp.util.common.Constants;

import java.util.List;

public class MovieItemDiffCallback extends Callback {

    private List<Movie> oldList, newList;
    MovieItemDiffCallback(List<Movie> oldList, List<Movie> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList == null ? 0 : oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList == null ? 0 : newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Movie oldItem = oldList.get(oldItemPosition);
        Movie newItem = newList.get(newItemPosition);
        return oldItem.getId() == newItem.getId() &&
        TextUtils.equals(oldItem.getReleaseDate(), newItem.getReleaseDate());
    }

    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Movie oldItem = oldList.get(oldItemPosition);
        Movie newItem = newList.get(newItemPosition);
        if (!oldItem.getReleaseDate().equals(newItem.getReleaseDate()))
            return Constants.UPDATE_DATE;
        else
            return null;
    }
}
