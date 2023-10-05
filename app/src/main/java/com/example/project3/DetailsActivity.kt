package com.example.project3

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.media.Rating
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException


private fun AsyncHttpClient.get(jsonHttpResponseHandler: JsonHttpResponseHandler) {

}

class DetailsActivity : AppCompatActivity() {
    private val movies = mutableListOf<Movie>()
    private lateinit var rvMovies: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailsactivitylayout)

        initializeViews()
        fetchNowPlayingMovies()
        val rvTitle = findViewById<TextView>(R.id.MovieTitle)
        val rvRating = findViewById<TextView>(R.id.RatingMovie)
        val rvMovieImg = findViewById<ImageView>(R.id.ImagePoster)

        initializeViews()
        fetchNowPlayingMovies()
    }


    private fun initializeViews() {
        rvMovies = findViewById(R.id.rvMovies)
        val movieAdapter = MovieAdapter(this, movies)

        rvMovies.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(this@DetailsActivity)
        }
    }

    private fun fetchNowPlayingMovies() {
        val client = AsyncHttpClient()

        client.get(, object : JsonHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                Log.e(TAG, "Request failed with code: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "Received JSON data: $json")

                try {
                    val movieJsonArray = json.jsonObject.getJSONArray("results")
                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                    rvMovies.adapter?.notifyDataSetChanged()
                    Log.i(TAG, "Movies retrieved: $movies")
                } catch (e: JSONException) {
                    Log.e(TAG, "Error parsing JSON: $e")
                }
            }
        })
    }
}



}