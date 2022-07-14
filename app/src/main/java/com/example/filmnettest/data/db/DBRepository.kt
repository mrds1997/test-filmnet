package com.example.filmnettest.data.db

class DBRepository(private val db: AppDataBase?= null) {

    fun saveVideo(video: Video) = db?.dao()?.insertVideo(video)

    fun getVideos() = db?.dao()?.getVideos()

    fun getVideoIdCount(videoId: String) = db?.dao()?.getVideoIdCount(videoId)

    suspend fun deleteVideo(video: Video) = db?.dao()?.deleteVideo(video)

}