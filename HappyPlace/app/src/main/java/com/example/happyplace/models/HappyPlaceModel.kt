package com.example.happyplace.models

data class HappyPlaceModel(
    val id: Int,
    val title: String,
    val image: String,
    val description: String,
    val location: String,
    val date: String,
    val latitude: Double,
    val longitude: Double,
)