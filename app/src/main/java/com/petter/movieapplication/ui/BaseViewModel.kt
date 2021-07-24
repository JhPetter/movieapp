package com.petter.movieapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    private val _errorLiveData: MutableLiveData<Throwable> by lazy { MutableLiveData<Throwable>() }
    val errorLiveData: LiveData<Throwable> get() = _errorLiveData

    fun fetchError(throwable: Throwable) {
        _errorLiveData.postValue(throwable)
    }
}