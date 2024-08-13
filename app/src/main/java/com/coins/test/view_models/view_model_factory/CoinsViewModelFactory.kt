package com.coins.test.view_models.view_model_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coins.test.repository.CoinsRepository
import com.coins.test.view_models.CoinsViewModel

class CoinsViewModelFactory(private val coinsRepository: CoinsRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CoinsViewModel(coinsRepository) as T
    }
}