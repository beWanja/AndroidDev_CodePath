package com.example.parstagram

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.parstagram.fragments.ComposeFragment
import com.example.parstagram.fragments.HomeFragment
import com.example.parstagram.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.FindCallback
import com.parse.ParseFile
import com.parse.ParseQuery
import com.parse.ParseUser
import java.io.File
import java.text.ParseException
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            item -> //Create a variable instead of calling a generic variable(enhances code readability

            var fragmentToShow: Fragment? = null
            when(item.itemId){

                R.id.action_home ->{
                    fragmentToShow = HomeFragment()
                }
                R.id.action_compose -> {
                    fragmentToShow = ComposeFragment()
                }
                R.id.action_profile -> {
                    fragmentToShow = ProfileFragment()
                }
            }

            if(fragmentToShow != null){
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
            }
            true
        }

        // Set default selection
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.action_home
        //queryPosts()
    }


    companion object{
        const val TAG = "MainActivity"
    }

}