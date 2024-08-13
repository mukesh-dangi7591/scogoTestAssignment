package com.coins.test.models.data_classes

import com.google.gson.annotations.SerializedName

data class Coin(
    val id: String,
    val name: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("is_new")
    val isNew: Boolean,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("type")
    val type: String
)