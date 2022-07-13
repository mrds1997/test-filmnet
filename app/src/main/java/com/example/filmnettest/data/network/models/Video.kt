package com.example.filmnettest.data.network.models

data class Video(
    val status: String?,
    val title: String?,
    val pageTitle: String?,
    val slug: String?,
    val summary: String?,
    val rate: Float?,
    val coverImage: Image?,
    val posterImage: Image?,
    val type: String?,
    val flag: String?,
    val conditionalFlag: String?,
    val ageRestriction: String?,
    val year: Int?,
    val imdbRankPercent: Float?,
    val originalName: String?,
    val categories: ArrayList<Category>?,
    val id: String?,
    val short_id: String?
)