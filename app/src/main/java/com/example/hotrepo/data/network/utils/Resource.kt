package com.example.hotrepo.data.network.utils

/**
 * Resource class will wrap the data with Network Status and Messages
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        OFFLINE
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> offline(data: T? = null): Resource<T> {
            return Resource(Status.OFFLINE, data, null)
        }
    }
}