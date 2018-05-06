package com.guilhermesan.peixeurbanooffers.ui.screens.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel() : ViewModel() {

    val disposeBag = CompositeDisposable()

    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun showLoading() {
        isLoading.value = true
    }

    fun hiddeLoading() {
        isLoading.value = false
    }

    override fun onCleared() {
        disposeBag.dispose()
        super.onCleared()
    }

}