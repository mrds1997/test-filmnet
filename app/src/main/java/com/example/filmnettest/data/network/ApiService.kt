package com.example.filmnettest.data.network
import com.example.filmnettest.data.network.models.response.GetVideosResponse
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @GET("search")
    suspend fun getVideosList(
        @Query("query") query: String?
    ): Response<GetVideosResponse>

}