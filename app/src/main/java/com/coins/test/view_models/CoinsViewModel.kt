package com.coins.test.view_models
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coins.test.models.data_classes.Coin
import com.coins.test.network_services.ResponseData
import com.coins.test.repository.CoinsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoinsViewModel(private val coinsRepository: CoinsRepository): ViewModel() {

    val coinData: LiveData<ResponseData<List<Coin>>>
        get() = coinsRepository.coinsList

    fun getCoinsData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    coinsRepository.getCoinList()
                } catch (e: Exception) {

                }
            }

        }
    }
}