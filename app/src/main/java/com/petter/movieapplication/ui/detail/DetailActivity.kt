package com.petter.movieapplication.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.petter.entities.Movie
import com.petter.movieapplication.databinding.ActivityDetailBinding
import com.petter.movieapplication.utils.MOVIE_ID
import com.petter.movieapplication.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailMovieViewModel by viewModels()

    companion object {
        fun start(context: Context, movieId: Int) {
            context.startActivity(Intent(context, DetailActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
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
        detailViewModel.fetchMovieById(getMovieId())
    }

    private fun getMovieId() = intent.getIntExtra(MOVIE_ID, 0)

    private fun observe() {
        lifecycleScope.launchWhenStarted {
            detailViewModel.movieLiveData.observe(this@DetailActivity) {
                configMovie(it)
            }
        }
    }

    private fun configMovie(movie: Movie) {
        with(binding) {
            detailImage.loadImage("https://image.tmdb.org/t/p/w780/${movie.bannerPath}")
            detailTitle.text = movie.title
            detailSummary.text = movie.summary
        }
    }
}