package com.guilhermesan.datamodule.dataprovider

import com.google.gson.Gson
import com.guilhermesan.datacontracts.dataproviders.DataProviderFactory
import com.guilhermesan.datacontracts.dataproviders.OfferDataProvider
import com.guilhermesan.datamodule.api.Api
import com.guilhermesan.datamodule.di.DaggerDataModuleComponent
import javax.inject.Inject

class DataProviderFactoryImp : DataProviderFactory {

    @Inject
    lateinit var api: Api

    @Inject
    lateinit var gson: Gson

    init {
        DaggerDataModuleComponent.builder().build().inject(this)
    }

    override fun getOfferDataProvider(): OfferDataProvider {
        return OfferDataProviderImp(api, gson)
    }

}