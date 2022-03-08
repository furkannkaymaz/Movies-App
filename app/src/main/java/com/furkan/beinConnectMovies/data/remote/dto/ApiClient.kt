package com.furkan.beinConnectMovies.data.remote.dto

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BaseWebservice = "https://api.themoviedb.org/3/"

object ApiClient {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BaseWebservice)
        .build()
        .create(ApiServices::class.java)

    fun run(): ApiServices = retrofit

}