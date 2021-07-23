package com.petter.movieapplication.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.petter.entities.MovieType
import com.petter.movieapplication.R
import com.petter.movieapplication.databinding.ActivityMainBinding
import com.petter.movieapplication.ui.movie.MovieFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var binding: ActivityMainBinding
    private val tabsList = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configTabBar()
    }

    private fun configTabBar() {
        tabsList.add(getString(R.string.movies))
        tabsList.add(getString(R.string.series))
        viewPagerAdapter = ViewPagerAdapter(
            supportFragmentManager,
            lifecycle,
            arrayListOf(
                MovieFragment.newInstance(MovieType.MOVIES),
                MovieFragment.newInstance(MovieType.TV)
            )
        )
        with(binding.homeAdapter) {
            adapter = viewPagerAdapter
            isUserInputEnabled = false
        }

        TabLayoutMediator(
            binding.homeTab,
            binding.homeAdapter
        ) { tab, position ->
            tab.text = tabsList[position]
        }.attach()
    }

}