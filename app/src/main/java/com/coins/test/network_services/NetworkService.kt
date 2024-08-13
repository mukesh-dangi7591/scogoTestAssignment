package com.coins.test.network_services

import com.coins.test.models.data_classes.Coin

import com.coins.test.utils.AppApiUrl.coins_end_point

import retrofit2.Response
import retrofit2.http.GET


interface NetworkService {


    @GET(coins_end_point)
    suspend fun getCoins(): Response<List<Coin>>

}