package com.example.filmnettest.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.bumptech.glide.Glide
import com.example.filmnettest.R
import com.example.filmnettest.data.network.models.Video
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_video_detail.*
import kotlinx.android.synthetic.main.adapter_category.view.*

class VideoDetailsActivity: LocalizationActivity() {

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
        videos?.forEach {video->
            if(video.id == id){
                Glide.with(this).load(video.coverImage?.path).into(imgCover)
                tvTitle.text = video.title
                tvSummary.text = video.summary
                tvRate.text = getString(R.string.rank, video.rate.toString())
                tvSummary.text = video.summary
                video.categories?.forEach {category ->
                    if(category.type == "genre"){
                        category.items.forEach { item->
                            val viewGenre = layoutInflater.inflate(R.layout.adapter_category, layoutCategory, false)
                            viewGenre.tvGenre.text  = item.title
                            layoutCategory.addView(viewGenre)
                        }
                    }
                }
            }
        }
    }

}