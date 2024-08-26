package com.example.mewfeline.utils

object HttpRoutes {

    fun getCats(limit: Int): String = "https://api.thecatapi.com/v1/images/search?limit=$limit"

    fun getCatDetails(imageId: String): String = "https://api.thecatapi.com/v1/images/$imageId"


}