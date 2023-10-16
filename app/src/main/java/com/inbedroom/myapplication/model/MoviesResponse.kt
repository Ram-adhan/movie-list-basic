package com.inbedroom.myapplication.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("Search") val list: List<MovieItemResponse>
)
