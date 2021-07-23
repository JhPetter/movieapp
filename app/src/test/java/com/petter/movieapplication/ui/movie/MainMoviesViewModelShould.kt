package com.petter.movieapplication.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.petter.entities.Movie
import com.petter.entities.MovieCategory
import com.petter.entities.MoviePage
import com.petter.entities.MovieType
import com.petter.movieapplication.util.RxImmediateSchedulerRule
import com.petter.movieapplication.viewobjects.MovieVO
import com.petter.usecases.usecase.MovieUseCase
import io.reactivex.rxjava3.core.Single
import junit.framework.TestCase.assertEquals
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MainMoviesViewModelShould {

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val movieUseCase: MovieUseCase = mock()
    private val movieType: MovieType = mock()

    private val movieTopRateCategory: MovieCategory = MovieCategory.TOP_RATE
    private val moviePopularCategory: MovieCategory = MovieCategory.TOP_RATE
    private val mockMovies: MoviePage = MoviePage(
        1,
        arrayListOf(
            Movie(
                1,
                "My Title",
                "summary",
                posterPath = "url",
                voteCount = 1,
                voteAverage = 1.1,
                bannerPath = "",
                releaseDate = null
            )
        ),
        10
    )

    private val mockMoviesPopular: MoviePage = MoviePage(
        1,
        arrayListOf(
            Movie(
                2,
                "My Title",
                "summary",
                posterPath = "url",
                voteCount = 1,
                voteAverage = 1.1,
                bannerPath = "",
                releaseDate = null
            )
        ),
        10
    )

    private val movieVO: MovieVO = mock()


    @Test
    fun getMoviesTopRateFromUseCase() {
        val viewModel = mockSuccess()
        val test = viewModel.fetchMovies(movieType).test()
        test.assertComplete()
            .assertNoErrors()
            .assertValueCount(1)
        viewModel.fetchMovieObject(movieType)
        /*viewModel.fetchMovieObject(movieType)
        verify(movieUseCase, times(2)).fetchMovies(movieType, moviePopularCategory)
        verify(movieUseCase, times(1)).fetchMovies(movieType, movieTopRateCategory)*/
    }

    @Test
    fun emitsMoviesTopRateFromUseCase() {
        val viewModel = mockSuccess()
        viewModel.fetchMovieObject(movieType)
        assertEquals(mockMovies.movies, viewModel.movieVOStateFlow.value)
    }

    /*
        @Test
        fun `when buildObservable then success`() {
            val rootModel = TERootModel(
                "Configurar Notificaciones",
                "Los cambios en tus notificaciones podrían demorar hasta 10 minutos",
                "Para recibir nuestras notificaciones tienes que habilitar los mensajes de Bci en la configuracion de tu telefono.",
                "Términos y condiciones disponibles en la sección Mi Banco de tu banco en línea en Bci.cl"
            )
            val textosEstaticosModel = TextosEstaticosModel(rootModel)

            val lista = listOf(
                ConfiguracionModel(
                    "nombre",  "descripcion",  "texto",1,0,0,"",true,12.00,12.00,"","","",tieneHijos = false
                )
            )
            val configuracionModel = ConfiguracionesModel(lista)

            Mockito.doReturn(Single.just(textosEstaticosModel)).`when`(mockRepositoryData)
                .getTextosEstaticos()
            Mockito.doReturn(Single.just(configuracionModel)).`when`(mockRepositoryData)
                .getObtenerConfiguraciones()

            val testObserver = sutUseCase.buildObservable(null).test()
            testObserver
                .assertSubscribed()
                .assertComplete()
                .assertNoErrors()
                .assertValueCount(1)

            Mockito.verify(mockRepositoryData).getTextosEstaticos()
            Mockito.verify(mockRepositoryData).getObtenerConfiguraciones()
        }
    */
    private fun mockSuccess(): MainViewModel {
        whenever(movieUseCase.fetchMovies(movieType, movieTopRateCategory)).thenReturn(
            Single.just(mockMovies)
        )
        whenever(movieUseCase.fetchMovies(movieType, moviePopularCategory)).thenReturn(
            Single.just(mockMoviesPopular)
        )
        return MainViewModel(movieUseCase)
    }
}