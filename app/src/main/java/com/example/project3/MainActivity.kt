package com.example.project3

import android.media.Rating
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "MainActivity"
private const val NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val RATING_TV = "https://api.themoviedb.org/3/rated/tv?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"



class MainActivity : AppCompatActivity() {
    private val movies = mutableListOf<Movie>()
    private lateinit var rvMovies: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        fetchNowPlayingMovies()
    }

    private fun initializeViews() {
        rvMovies = findViewById(R.id.rvMovies)
        val movieAdapter = MovieAdapter(this, movies)

        rvMovies.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun fetchNowPlayingMovies() {
        val client = AsyncHttpClient()

        client.get(NOW_PLAYING_URL, object : JsonHttpResponseHandler() {
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
