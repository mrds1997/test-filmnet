package com.example.filmnettest.ui.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.bumptech.glide.Glide
import com.example.filmnettest.data.db.DbViewModelProviderFactory
import com.example.filmnettest.R
import com.example.filmnettest.data.db.DBRepository
import com.example.filmnettest.data.db.DataBaseClient
import com.example.filmnettest.data.db.DbViewModel
import com.example.filmnettest.data.network.models.Video
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_video_detail.*
import kotlinx.android.synthetic.main.adapter_category.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VideoDetailsActivity : LocalizationActivity() {

    private var videos: ArrayList<Video>? = null
    private var videoDetails: Video? = null
    private var id: String? = null
    private var forFavorite: Int? = null
    private var videoJsonFromDb: String? = null
    lateinit var dbViewModel: DbViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)

        val videosJson = intent?.getStringExtra("videosJson")

        id = intent?.getStringExtra("id")

        forFavorite = intent.getIntExtra("forFavorite", 0)

        val gson = Gson()
        val type = object : TypeToken<ArrayList<Video?>?>() {}.getType()
        videos = gson.fromJson(videosJson, type)

        loadDetails()

        setUpViewModel()

        if(forFavorite == 1){
            fabAddToFavorite.visibility = View.INVISIBLE
        }

        fabAddToFavorite.setOnClickListener {
            if (videoDetails != null) {
                addToFavorite()

            }
        }
    }


    private fun setUpViewModel() {
        val db = DataBaseClient.getInstance(this)?.getAppDatabase()
        val dbRepository = DBRepository(db)
        val dbViewModelProviderFactory = DbViewModelProviderFactory(dbRepository)
        dbViewModel =
            ViewModelProvider(this, dbViewModelProviderFactory).get(DbViewModel::class.java)
    }

    private fun loadDetails() {
        videos?.forEach { video ->
            if (video.id == id) {
                videoDetails = video
                Glide.with(this).load(video.coverImage?.path).into(imgCover)
                tvTitle.text = video.title
                tvSummary.text = video.summary
                tvRate.text = getString(R.string.rank, video.rate.toString())
                tvSummary.text = video.summary
                video.categories?.forEach { category ->
                    if (category.type == "genre") {
                        category.items.forEach { item ->
                            layoutCategory.removeAllViews()
                            val viewGenre = layoutInflater.inflate(
                                R.layout.adapter_category,
                                layoutCategory,
                                false
                            )
                            viewGenre.tvGenre.text = item.title
                            layoutCategory.addView(viewGenre)
                        }
                    }
                }
            }
        }
    }

    private fun addToFavorite() {
        lifecycleScope.launch(Dispatchers.IO) {
            if (dbViewModel.getVideoIdCount(videoDetails?.id!!) == 0) {
                dbViewModel.saveVideo(
                    com.example.filmnettest.data.db.Video(
                        title = videoDetails!!.title,
                        summary = videoDetails!!.summary,
                        rate = videoDetails!!.rate,
                        coverImage = videoDetails!!.coverImage,
                        posterImage = videoDetails!!.posterImage,
                        categories = videoDetails!!.categories,
                        videoId = videoDetails!!.id
                    )
                )
                Snackbar.make(layoutRoot, getString(R.string.added_suceesfully), Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(layoutRoot, getString(R.string.exist_in_favorite), Snackbar.LENGTH_LONG).show()
            }
        }
    }

}