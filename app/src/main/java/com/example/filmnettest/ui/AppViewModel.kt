package com.example.filmnettest.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmnettest.data.network.Repository
import com.example.filmnettest.data.network.Resource
import com.example.filmnettest.data.network.models.response.GetVideosResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class AppViewModel(private val repository: Repository) : ViewModel() {

    val moviesList: MutableLiveData<Resource<GetVideosResponse>> = MutableLiveData()
    suspend fun getMoviesList(searchQuery: String?) = withContext(Dispatchers.IO) {
        moviesList.postValue(Resource.Loading())
        val response = repository.getMoviesList(searchQuery)
        moviesList.postValue(handleResponse(response))
    }


    private fun handleResponse(response: Response<GetVideosResponse>): Resource<GetVideosResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}