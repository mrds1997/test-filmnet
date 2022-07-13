package com.example.filmnettest.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.filmnettest.R
import com.example.filmnettest.data.network.models.Video
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_video_detail.*

class VideoDetailsActivity: AppCompatActivity() {

    private var videos: ArrayList<Video>? = null
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)

        val videosJson = intent?.getStringExtra("videosJson")
        id = intent?.getStringExtra("id")

        val gson = Gson()
        val type = object : TypeToken<ArrayList<Video?>?>() {}.getType()
        videos = gson.fromJson(videosJson, type)

        loadDetails()



    }

    private fun loadDetails() {
        videos?.forEach {
            if(it.id == id){
                Glide.with(this).load(it.coverImage?.path).into(imgCover)
                tvTitle.text = it.title
                tvSummary.text = it.summary
                tvRate.text = getString(R.string.rank, it.rate.toString())
                tvSummary.text = it.summary
            }
        }
    }

}