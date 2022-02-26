package com.example.flixter

import org.json.JSONArray

//We want to represent on movie object that stores data common for each movie
data class Movie (
    val title: String,
    val movieId: Int,
    val overview: String,
    val posterPath: String,

){
    val posterImageUrl ="https://image.tmdb.org/t/p/w342/$posterPath"

    companion object{ //Allows us to call methods on the movie class without having a movie instance
        fun fromJsonArray(movieJsonArray: JSONArray): List<Movie>{
            val movies = mutableListOf<Movie>()
            for(i in 0 until movieJsonArray.length()){
                val movieJson = movieJsonArray.getJSONObject(i)
                movies.add(
                    Movie(
                        movieJson.getString("title"),
                        movieJson.getInt("id"),
                        movieJson.getString("overview"),
                        movieJson.getString("poster_path"),

                    )
                )
            }
            return movies
        }
    }
}