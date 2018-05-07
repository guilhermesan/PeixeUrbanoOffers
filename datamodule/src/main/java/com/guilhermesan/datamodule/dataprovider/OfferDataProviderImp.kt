package com.guilhermesan.datamodule.dataprovider

import android.util.Log
import com.google.gson.Gson
import com.guilhermesan.datacontracts.dataproviders.OfferDataProvider
import com.guilhermesan.datacontracts.vos.Offer
import com.guilhermesan.datamodule.api.Api
import io.reactivex.Observable
import javax.inject.Inject

class OfferDataProviderImp @Inject constructor(val api: Api, val gson: Gson) : OfferDataProvider{

    companion object {
        const val appSecret = "QasVmHaL55PPbMvPiDJe"
    }

    override fun getOffers(lat: Double, long: Double, offset: Int, limit: Int): Observable<List<Offer>> {
        Log.i("call", "Lag: $lat long: $long offset: $offset limit: $limit")
        return api.getOffers(appSecret,lat,long,offset,limit).map {
            val list = ArrayList<Offer>()
            val response = it.asJsonObject["response"].asJsonArray
            response.map {
                val offer = gson.fromJson(it,Offer::class.java)
                list.add(offer)
            }
            list
        }
    }

}