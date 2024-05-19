package com.inbedroom.myapplication.data

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.inbedroom.myapplication.BuildConfig
import com.inbedroom.myapplication.model.MovieItemResponse
import com.inbedroom.myapplication.model.MovieRequest
import com.inbedroom.myapplication.model.MoviesResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime
import kotlin.time.measureTimedValue

class RepositoryClass {
    private fun client(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(20, TimeUnit.SECONDS)
        .callTimeout(20, TimeUnit.SECONDS)
        .build()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        this.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    suspend fun getMovies(): List<MovieItemResponse>? {
        val request = Request.Builder()
            .url("https://movie-database-alternative.p.rapidapi.com/?s=Avengers%20Endgame&r=json&page=1")
            .get()
            .addHeader("X-RapidAPI-Key", "1d66af08dbmsh9d3febca7b83032p1523f6jsnf2ddab54bcfe")
            .addHeader("X-RapidAPI-Host", "movie-database-alternative.p.rapidapi.com")
            .build()

        return try {
            val response = client().newCall(request).execute()
            if (response.isSuccessful) {
                val typeToken = object : TypeToken<MoviesResponse>() {}.type
                val data = Gson().fromJson<MoviesResponse>(response.body?.string(), typeToken)
                data.list
            } else {
                null
            }
        } catch (e: java.lang.Exception) {
            null
        }
    }
}