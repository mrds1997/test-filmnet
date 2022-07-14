package com.example.filmnettest.data.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DbViewModel(private val repository: DBRepository) : ViewModel() {

    fun getVideoIdCount(videoId: String) = repository.getVideoIdCount(videoId)

    fun getVideos() = repository.getVideos()

    suspend fun saveVideo(video: Video) = withContext(Dispatchers.IO) {
        repository.saveVideo(video)
    }

    suspend fun deleteVideoFromDb(video: Video) = withContext(Dispatchers.IO){
        repository.deleteVideo(video)
    }


}