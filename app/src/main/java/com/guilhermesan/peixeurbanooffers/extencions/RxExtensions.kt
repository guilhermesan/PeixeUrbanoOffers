package com.guilhermesan.peixeurbanooffers.extencions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.addToDisposeBag(disposeBag:CompositeDisposable){
    disposeBag.add(this)
}