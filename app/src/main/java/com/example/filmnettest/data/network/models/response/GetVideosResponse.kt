package com.example.filmnettest.data.network.models.response

import com.example.filmnettest.data.network.models.Data
import com.example.filmnettest.data.network.models.Meta

data class GetVideosResponse(
    val data: Data,
    val meta: Meta
)