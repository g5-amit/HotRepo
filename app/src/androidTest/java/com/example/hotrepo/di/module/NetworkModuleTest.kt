package com.example.hotrepo.di.module

import com.example.hotrepo.data.network.apiService.RemoteApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Test Module used for getting retrofit client for MockServer configuration and get mock response
 * */
@Module
@InstallIn(ApplicationComponent::class)
class NetworkModuleTest {

    private val READ_TIMEOUT = 15L
    private val WRITE_TIMEOUT = 60L
    private val CONNECTION_TIMEOUT = 15L

    @Singleton
    @Provides
    fun giveRetrofitAPIService(gson: Gson, client: OkHttpClient): RemoteApiService =
        Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(RemoteApiService::class.java)

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun getOkHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
        return clientBuilder.build()
    }

}