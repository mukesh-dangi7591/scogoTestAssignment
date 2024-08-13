package com.coins.test.network_services

import com.coins.test.utils.AppApiUrl.baseUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

   // private const val BASE_URL = "https://api.coinpaprika.com/v1/"

    private val retrofitHelper: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val networService: NetworkService by lazy {
        retrofitHelper.create(NetworkService::class.java)
    }
}