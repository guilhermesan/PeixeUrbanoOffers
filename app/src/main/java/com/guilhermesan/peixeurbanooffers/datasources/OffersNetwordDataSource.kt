package com.guilhermesan.peixeurbanooffers.datasources

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.PositionalDataSource
import com.guilhermesan.datacontracts.dataproviders.OfferDataProvider
import com.guilhermesan.datacontracts.vos.Offer
import com.guilhermesan.peixeurbanooffers.extencions.addToDisposeBag
import io.reactivex.disposables.CompositeDisposable

class OffersDataSource(
        private val offersDataProvider: OfferDataProvider,
        private val disposeBag: CompositeDisposable,
        private val lat:Double,
        private val long:Double)
    : PositionalDataSource<Offer>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Offer>) {
        offersDataProvider.getOffers(lat, long, params.startPosition, params.loadSize).subscribe({
            callback.onResult(it)
        }).addToDisposeBag(disposeBag)
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Offer>) {
        offersDataProvider.getOffers(lat, long, params.requestedStartPosition, params.requestedLoadSize).subscribe({
            callback.onResult(it,params.requestedStartPosition)
        }).addToDisposeBag(disposeBag)
    }


}

class OffersDataSourceFactory(private val offersDataProvider: OfferDataProvider,
                              private val disposeBag: CompositeDisposable,
                              private val lat:Double,
                              private val long:Double)
    : DataSource.Factory<Integer,Offer>() {

    val offersDataSourceLiveData = MutableLiveData<OffersDataSource>()

    override fun create(): DataSource<Integer, Offer> {
        val dataSource = OffersDataSource(offersDataProvider, disposeBag, lat, long)
        offersDataSourceLiveData.postValue(dataSource)
        return dataSource as DataSource<Integer, Offer>
    }


}