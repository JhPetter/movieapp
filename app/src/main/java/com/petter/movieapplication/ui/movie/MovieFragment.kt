package com.petter.movieapplication.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.petter.entities.MovieType
import com.petter.movieapplication.R
import com.petter.movieapplication.utils.MOVIE_TYPE

class MovieFragment : Fragment() {
    private lateinit var movieType: MovieType


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieType = MovieType.valueOf(it.getString(MOVIE_TYPE, MovieType.MOVIES.name))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(movieType: MovieType) =
            MovieFragment().apply {
                arguments = Bundle().apply {
                    putString(MOVIE_TYPE, movieType.name)
                }
            }
    }
}