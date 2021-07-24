package com.petter.movieapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.petter.movieapplication.components.CrossDialog

abstract class BaseActivity : AppCompatActivity() {
    abstract fun fetchErrorToObserve(): LiveData<Throwable>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchErrorToObserve().observe(this) {
            showDialog(it)
        }
    }

    private fun showDialog(throwable: Throwable) {
        CrossDialog(throwable.message ?: "").show(
            supportFragmentManager,
            CrossDialog::class.java.name
        )
    }
}