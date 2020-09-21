package com.example.hotrepo.data.network.utils

suspend fun <T> performNetworkOperation(
    networkCall: suspend () -> Resource<T>,
    saveCallResult: suspend (T) -> Unit
): Resource<T?> {

    val responseStatus = networkCall.invoke()
    return when(responseStatus.status){
        Resource.Status.SUCCESS -> {
            (responseStatus.data)?.let { saveCallResult(responseStatus.data) }
            Resource.success(responseStatus.data)
        }
        Resource.Status.ERROR ->{
            Resource.error(responseStatus.message ?: "")
        }
        Resource.Status.OFFLINE ->{
            Resource.offline()
        }
    }
}
