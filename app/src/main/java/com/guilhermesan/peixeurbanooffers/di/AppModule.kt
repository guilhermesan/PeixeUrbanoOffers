package com.guilhermesan.peixeurbanooffers.di

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import com.guilhermesan.datacontracts.dataproviders.DataProviderFactory
import com.guilhermesan.datacontracts.dataproviders.OfferDataProvider
import com.guilhermesan.datamodule.dataprovider.DataProviderFactoryImp
import com.guilhermesan.peixeurbanooffers.OffersApp
import com.guilhermesan.peixeurbanooffers.ui.screens.offers.OfferAdapter
import com.guilhermesan.peixeurbanooffers.ui.screens.offers.OffersActivity
import com.guilhermesan.peixeurbanooffers.ui.screens.offers.OffersViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [AppModule::class])
    abstract fun offersActivity():OffersActivity

}


@Module()
open class AppModule {

    @Provides
    fun provideContext(application: OffersApp): Context {
        return application.applicationContext
    }

    @Provides
    fun providesDataProviderFactory(): DataProviderFactory{
        return DataProviderFactoryImp()
    }

    @Provides
    fun providesOfferDataProvier(dataProviderFactory: DataProviderFactory): OfferDataProvider{
        return dataProviderFactory.getOfferDataProvider()
    }

    @Provides
    fun provideOffersViewModel(offersActivity: OffersActivity): OffersViewModel{
        return ViewModelProviders.of(offersActivity).get(OffersViewModel::class.java)
    }

    @Provides
    fun providesOfferAdapter() : OfferAdapter{
        return OfferAdapter()
    }
}
