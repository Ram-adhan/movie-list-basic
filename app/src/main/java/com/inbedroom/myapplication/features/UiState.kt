package com.inbedroom.myapplication.features

import com.inbedroom.myapplication.model.MovieItemResponse

sealed interface UiState {
    data class Success(val data: List<MovieItemResponse>): UiState
    object Failed: UiState
}