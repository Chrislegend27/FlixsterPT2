package com.example.project3

import org.json.JSONArray


data class Movie(
    val movieId: Int,
    val posterPath: String,
    val title: String,
    val overview: String
) {

    val posterImageUrl = "https://images.tmdb.org/t/p/w342/$posterPath"

    companion object {

        fun fromJsonArray(movieJsonArray: JSONArray): List<Movie> {
            val movies = mutableListOf<Movie>()


            for (i in 0 until movieJsonArray.length()) {
                val movieJson = movieJsonArray.getJSONObject(i)
                val movie = Movie(
                    movieJson.getInt("id"),               // Movie ID
                    movieJson.getString("poster_path"),  // Poster path
                    movieJson.getString("title"),        // Movie title
                    movieJson.getString("overview")      // Movie overview
                )

                // Add the created Movie object to the list
                movies.add(movie)
            }

            // Return the list of Movie objects
            return movies
        }
    }
}
