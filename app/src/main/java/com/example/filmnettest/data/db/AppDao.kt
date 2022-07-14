package com.example.filmnettest.data.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideo(video: Video) : Long

    @Query("SELECT * FROM video")
    fun getVideos(): LiveData<List<Video>>

    @Delete
    fun deleteVideo(video: Video)

    @Query("SELECT COUNT(*) FROM video WHERE videoId = :videoId")
    fun getVideoIdCount(videoId: String): Int

}