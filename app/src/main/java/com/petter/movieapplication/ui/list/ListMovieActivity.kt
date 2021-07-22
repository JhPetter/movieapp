package com.petter.movieapplication.ui.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.petter.entities.MoviePage
import com.petter.entities.MovieType
import com.petter.movieapplication.R
import com.petter.movieapplication.databinding.ActivityListMovieBinding

class ListMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListMovieBinding

    companion object {
        fun start(context: Context, moviePage: MoviePage, movieType: MovieType) {
            context.startActivity(Intent(context, ListMovieActivity::class.java).apply {

            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}