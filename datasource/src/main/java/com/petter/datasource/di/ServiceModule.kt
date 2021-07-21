package com.petter.datasource.di

import com.petter.datasource.mapper.MovieMapper
import com.petter.datasource.mapper.MoviePageMapper
import com.petter.datasource.mapper.MoviesListMapper
import com.petter.datasource.service.ApiService
import com.petter.datasource.source.MovieService
import com.petter.usecases.repository.IMoviesServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    fun provideMoviesListMapper(): MoviesListMapper = MoviesListMapper()

    @Provides
    fun provideMoviePageMapper(moviesListMapper: MoviesListMapper): MoviePageMapper =
        MoviePageMapper(moviesListMapper)

    @Provides
    fun provideMovieMapper(): MovieMapper = MovieMapper()

    @Provides
    fun provideMovieService(
        iApiService: ApiService,
        moviePageMapper: MoviePageMapper,
        movieMapper: MovieMapper
    ): IMoviesServiceRepository =
        MovieService(iApiService, moviePageMapper, movieMapper)
}