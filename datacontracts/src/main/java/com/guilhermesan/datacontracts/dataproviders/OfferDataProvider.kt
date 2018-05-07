package com.guilhermesan.datacontracts.dataproviders

import com.guilhermesan.datacontracts.vos.Offer
import io.reactivex.Observable

interface OfferDataProvider {

    fun getOffers(lat:Double, long:Double, offset:Int,limit:Int): Observable<List<Offer>>
}