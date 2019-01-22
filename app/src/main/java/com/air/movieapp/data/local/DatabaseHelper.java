package com.air.movieapp.data.local;

import com.air.movieapp.data.model.Movie;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class DatabaseHelper {

    private Realm mRealm;

    public DatabaseHelper(Realm mRealm) {
        this.mRealm = mRealm;
    }

    public Realm getRealm() {
        return mRealm;
    }

    public void saveMovieList(String categoryName, final List<Movie> movies) {
        setCategoryToMovies(categoryName, movies);
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(movies);
        mRealm.commitTransaction();
    }

    private void setCategoryToMovies(String categoryName, List<Movie> movies) {
        if (movies != null && !movies.isEmpty()) {
            for (Movie movie : movies) {
                movie.setType(categoryName);
            }
        }
    }

    public Movie getMovie(Integer id) {
        return mRealm.where(Movie.class)
                .equalTo("id", id)
                .findFirst();
    }

    public List<Movie> getMovies(String category) {
        return mRealm.where(Movie.class)
                .equalTo("type", category)
                .findAll();
    }

    public void updateMovieList(List<Movie> movies){
        mRealm.copyToRealmOrUpdate(movies);
    }

    public void deleteAllMovies(String category){
        RealmResults<Movie> results = mRealm.where(Movie.class).equalTo("type", category).findAll();
        mRealm.beginTransaction();
        results.deleteAllFromRealm();
        mRealm.commitTransaction();
    }
}
