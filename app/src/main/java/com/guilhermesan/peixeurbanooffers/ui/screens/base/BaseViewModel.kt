package com.guilhermesan.peixeurbanooffers.ui.screens.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.guilhermesan.peixeurbanooffers.OffersApp
import com.guilhermesan.peixeurbanooffers.di.AppComponent
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

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

    fun getComponent():AppComponent {
        return getApplication<OffersApp>().appComponent
    }

}