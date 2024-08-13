package com.coins.test.network_services

sealed class ResponseData<T>(var data:T?=null, var errorMessage:String? = null) {
class Loading<T>():ResponseData<T>()
class Success<T>(data: T? = null):ResponseData<T>(data = data)
class Error<T>(errorMessage: String?):ResponseData<T>(errorMessage = errorMessage)

}