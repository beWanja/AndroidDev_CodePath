package com.codepath.apps.restclienttemplate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class ComposeActivity : AppCompatActivity() {

    lateinit var etCompose: EditText
    lateinit var btnTweet: Button
    lateinit var client : TwitterClient
    lateinit var tvCharCounter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)

        etCompose = findViewById(R.id.etComposeTweet)
        btnTweet = findViewById(R.id.btnTweet)
        client= TwitterApplication.getRestClient(this)
        tvCharCounter = findViewById(R.id.tvCharCounter)



        etCompose.addTextChangedListener(object: TextWatcher{

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?,
                                           start: Int,
                                           count: Int,
                                           after: Int) {
            }

            //Implement the counter and button disabling logic here
            override fun onTextChanged(s: CharSequence?,
                                       start: Int,
                                       before: Int,
                                       count: Int) {
                val tweetContent = etCompose.text.toString()
                tvCharCounter.setText("" + count)
                //Disable button if tweet is longer than 280 characters
                if(tweetContent.isNotEmpty() && tweetContent.length <= 280){
                    btnTweet.isEnabled = true
                    //else, the Tweet button will be disabled
                }
            }



        })

        btnTweet.setOnClickListener{
            //Get the content in etComposeTweet
            val tweetContent = etCompose.text.toString()
            //Constrains: 1 - 140** characters
            if(tweetContent.isEmpty()){
                Toast.makeText(this, "Cannot publish empty " +
                        "tweet!", Toast.LENGTH_SHORT).show()
            }else if(tweetContent.length > 280){
                Toast.makeText(this, "Tweet too long! " +
                        "Limit is 280 characters.", Toast.LENGTH_SHORT).show()
            }else{
                client.publishTweet(tweetContent,object: JsonHttpResponseHandler(){
                    override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                        Log.i(TAG, "Successful tweet")

                        val tweet = Tweet.fromJson((json.jsonObject))

                        val intent  = Intent()
                        intent.putExtra("tweet", tweet)
                        setResult(RESULT_OK, intent)
                        finish()
                        //Send tweet to update on Timeline Activity
                        //without making an extra API call
                    }
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String?,
                        throwable: Throwable?
                    ) {
                        Log.e(TAG, "Failed to publish tweet", throwable)
                    }
                })
            }
        }
    }

    companion object {
        val TAG = "ComposeActivity"
    }
}