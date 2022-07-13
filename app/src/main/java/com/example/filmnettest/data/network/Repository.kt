package com.example.filmnettest.data.network


class Repository(private val apiService: ApiService) {

    suspend fun getMoviesList(searchQuery: String?) = apiService.getMoviesList(searchQuery)


}