package com.example.filmnettest.data.network.models

data class Video(
    val status: String?=null,
    val title: String?=null,
    val pageTitle: String?=null,
    val slug: String?=null,
    val summary: String?=null,
    val rate: Float?=null,
    val coverImage: Image?=null,
    val posterImage: Image?=null,
    val type: String?=null,
    val flag: String?=null,
    val conditionalFlag: String?=null,
    val ageRestriction: String?=null,
    val year: Int?=null,
    val imdbRankPercent: Float?=null,
    val originalName: String?=null,
    val categories: ArrayList<Category>?=null,
    val id: String?=null,
    val short_id: String? = null
)