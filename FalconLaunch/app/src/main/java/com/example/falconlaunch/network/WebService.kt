package com.example.falconlaunch.network

import com.example.falconlaunch.models.ApiData
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


const val BASE_URL = "https://api.spacexdata.com/v4/"

/**
 *  Api handler
 */
object Api {
    val retrofitService: ApiService by lazy {
        retrofit().create(ApiService::class.java)
    }
}

/**
 * OkHttpClient
 */
fun getUnsafeOkHttpClient(): OkHttpClient {
    val dispatcher = Dispatcher()
    dispatcher.maxRequests = 1

    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.HEADERS
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    val builder = OkHttpClient.Builder()
    builder.addInterceptor(interceptor)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)

    return builder.build()
}

private fun retrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getUnsafeOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}


interface ApiService {

    @GET("launches")
    fun launches(): Call<ArrayList<ApiData>>
}



