package com.example.flixter

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

class MainActivity : AppCompatActivity() {
    private val movies = mutableListOf<Movie>()
    private lateinit var rvMovies : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMovies = findViewById(R.id.rvMovies)
        val movieAdapter = MovieAdapter(this, movies)

        rvMovies.adapter = movieAdapter
        rvMovies.layoutManager = LinearLayoutManager(this)  //Puts the views vertically

        val client = AsyncHttpClient()
        client.get(NOW_PLAYING_URL, object: JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "onFailure $statusCode")  //Logging at error level
            }
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "onSuccess: JSON data $json") //Logging at info level** Meaning?
                try{
                    val movieJsonArray = json.jsonObject.getJSONArray("results")  //movieJsonArray represents the set of data stored in results
                    movies.addAll(Movie.fromJsonArray(movieJsonArray)) //Note: Might throw a json exception and cause app to crash
                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "Movie list $movies")
                } catch(e:JSONException){
                    Log.e(TAG, "Encountered exception $e")
                }

            }

        })

    }
}