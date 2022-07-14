package com.example.filmnettest.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.filmnettest.data.network.models.Category
import com.example.filmnettest.data.network.models.Image

@Entity(tableName = "video", indices = [Index(value = ["videoId"], unique = true)])
data class Video(
    @PrimaryKey
    val id: Int? = null,
    val title: String?,
    val summary: String?,
    val rate: Float?,
    val coverImage: Image?,
    val posterImage: Image?,
    val categories: ArrayList<Category>?,
    val videoId: String?,
)
