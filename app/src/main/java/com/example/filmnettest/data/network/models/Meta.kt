package com.example.filmnettest.data.network.models

data class Meta(
    val displayMessage: String,
    val nextUrl: String,
    val operationResult: String,
    val operationResultCode: Int,
    val remainingItemsCount: Int,
    val serverDateTime: String,
    val totalItemsCount: Int
)