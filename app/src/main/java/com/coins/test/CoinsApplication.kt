package com.coins.test

import android.app.Application
import com.coins.test.network_services.NetworkService
import com.coins.test.network_services.RetrofitHelper
import com.coins.test.repository.CoinsRepository


class CoinsApplication : Application() {
    lateinit var coinsRepository: CoinsRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize(){
         val apiService: NetworkService = RetrofitHelper.networService
        coinsRepository = CoinsRepository(apiService)
    }
}