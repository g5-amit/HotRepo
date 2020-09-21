package com.example.hotrepo.data.networkWrapper

import android.content.Context
import android.util.Log
import com.example.hotrepo.utility.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response

/**
 * Wrapper to get different state like success, offline and error which returns data over Resource Object
 */
abstract class BaseDataSource(@ApplicationContext val appContext: Context) {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            if(!(NetworkUtils.hasNetworkAvailable(appContext))){
                return Resource.offline()
            }
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Log.d("remoteDataSource", message)
        return Resource.error(message)
    }

}