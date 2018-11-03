package com.example.android.movieapponetestone.api;

import com.example.android.movieapponetestone.model.popular.PopularResult;
import com.example.android.movieapponetestone.model.movies.ReviewResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("movie/popular")
    Call<PopularResult> getPopularMovies(
            @Query("api_key") String key);

    @GET("movie/top_rated")
    Call<PopularResult> getTopRatedMovies(
            @Query("api_key") String key);


    @GET("movie/{movie_id}/reviews")
    Call<ReviewResult> getReviews(
            @Path("movie_id") int id,
            @Query("api_key") String key);
}
