package com.coins.test.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.coins.test.models.data_classes.Coin
import com.coins.test.network_services.NetworkService
import com.coins.test.network_services.ResponseData

class CoinsRepository(private val networkService: NetworkService) {
    private val userLiveCoinse = MutableLiveData<ResponseData<List<Coin>>>()
    val coinsList:LiveData<ResponseData<List<Coin>>>
        get() = userLiveCoinse

    suspend fun getCoinList() {
        try {
            val result = networkService.getCoins();
            if (result.body()!=null){
                userLiveCoinse.postValue(ResponseData.Success(result.body()))
            }else{
                userLiveCoinse.postValue(ResponseData.Error("Some error accure during api call"))
            }
        }catch (e:Exception){
            println("errorMessage:- ${e.message.toString()}")
            userLiveCoinse.postValue(ResponseData.Error(e.message.toString()))
        }

    }


}