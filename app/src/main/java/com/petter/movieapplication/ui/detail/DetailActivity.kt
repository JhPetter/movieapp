package com.petter.movieapplication.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.petter.entities.Movie
import com.petter.entities.MovieType
import com.petter.movieapplication.databinding.ActivityDetailBinding
import com.petter.movieapplication.ui.BaseActivity
import com.petter.movieapplication.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailMovieViewModel by viewModels()

    companion object {
        fun start(context: Context, movieId: Int, movieType: MovieType) {
            context.startActivity(Intent(context, DetailActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
                putExtra(MOVIE_TYPE, movieType.name)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpView()
        observe()
    }

    private fun setUpView() {
        binding.detailBack.setOnClickListener { onBackPressed() }
        detailViewModel.fetchMovieById(getMovieId(), getMovieType())
    }

    private fun getMovieId() = intent.getIntExtra(MOVIE_ID, 0)
    private fun getMovieType() =
        MovieType.valueOf(intent.getStringExtra(MOVIE_TYPE) ?: MovieType.MOVIES.name)

    private fun observe() {
        lifecycleScope.launchWhenStarted {
            detailViewModel.movieLiveData.observe(this@DetailActivity) {
                configMovie(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            detailViewModel.detailLoadingSateFlow.collect {
                binding.shimmerDetail.visibility = it
            }
        }

        lifecycleScope.launchWhenStarted {
            detailViewModel.detailDataSateFlow.collect {
                with(binding) {
                    detailBack.visibility = it
                    detailTitleSummary.visibility = it
                }
            }
        }
    }

    override fun fetchErrorToObserve(): LiveData<Throwable> = detailViewModel.errorLiveData

    private fun configMovie(movie: Movie) {
        with(binding) {
            detailImage.loadImage("https://image.tmdb.org/t/p/w780/${movie.bannerPath}")
            detailTitle.text = movie.title
            detailSummary.text = movie.summary
            detailChips.addChips(layoutInflater, movie.genres)
            detailDate.text = movie.releaseDate?.formatMonthDayYear(this@DetailActivity)
        }
    }
}