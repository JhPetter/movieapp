package com.petter.movieapplication.di

import com.petter.movieapplication.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    @Named("api_key")
    fun provideApiKey(): String = BuildConfig.API_KEY

    @Provides
    @Singleton
    @Named("base_url")
    fun provideBaseUrl(): String = BuildConfig.BASE_URL
}