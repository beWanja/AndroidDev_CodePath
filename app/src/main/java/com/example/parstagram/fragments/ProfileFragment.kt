package com.example.parstagram.fragments

import android.util.Log
import com.example.parstagram.Post
import com.parse.FindCallback
import com.parse.ParseQuery
import com.parse.ParseUser

class ProfileFragment: HomeFragment() {
    override fun queryPosts(){
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)

        //Restricts posts returned to be the ones by user currently signed in
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())

        query.addDescendingOrder("createdAt")
        query.findInBackground(object: FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: com.parse.ParseException?) {
                if(e != null){
                    Log.e(TAG, "Error fetching posts!")
                }else{
                    if(posts != null){
                        for(post in posts){
                            Log.i(
                                TAG, "Post " + post.getDescription() +", from: " +
                                        post.getUser()?.username)
                        }

                        allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}