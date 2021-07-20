package com.petter.datasource.di

import com.petter.datasource.service.ApiService
import com.petter.datasource.util.ApiInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    fun provideApiInterceptor(@Named("api_key") apiKey: String): ApiInterceptor =
        ApiInterceptor(apiKey)

    @Provides
    fun provideOkHttpClient(
        apiInterceptor: ApiInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            addInterceptor(apiInterceptor)
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()
    }

    @Provides
    fun provideRetrofit(@Named("base_url") baseUrl: String, client: OkHttpClient): Retrofit =
        Retrofit.Builder().apply {
            client(client)
            baseUrl(baseUrl)
            addConverterFactory(GsonConverterFactory.create())
        }.build()

    @Provides
    fun provideApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}