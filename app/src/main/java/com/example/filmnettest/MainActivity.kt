package com.example.filmnettest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.filmnettest.ui.fragment.VideosFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var currentFragment: Fragment? = null
    private var videosFragment: VideosFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.videos -> {
                    changeFragment(getVideosFragment())
                }
                R.id.favorites -> {
                    //changeFragment(getCallFragment())
                }
            }
            true
        }
        bottomNavigationView.selectedItemId = R.id.videos
    }

    private fun changeFragment(fragment: Fragment) {
        //if(!fragment.isAdded) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.layoutFragment, fragment)
            commit()
            //addToBackStack(null)
        }
        //supportFragmentManager.popBackStack()
        //}

        currentFragment = fragment
    }

    private fun getVideosFragment(): Fragment {
        if (videosFragment == null) {
            videosFragment = VideosFragment.newInstance()
        }
        return videosFragment as VideosFragment
    }


    override fun onBackPressed() {
        if (currentFragment === getVideosFragment()) {
            showExitToast()
        } else {
            bottomNavigationView.selectedItemId = R.id.videos
        }
    }

    private var backPressedTime: Long = 0
    private fun showExitToast() {
        val t = System.currentTimeMillis()
        if (t - backPressedTime > 2000) {
            backPressedTime = t
            Toast.makeText(this, getString(R.string.message_exit), Toast.LENGTH_LONG).show()
        } else {
            super.onBackPressed()
        }
    }


}