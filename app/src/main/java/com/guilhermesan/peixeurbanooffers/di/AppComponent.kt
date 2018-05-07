package com.guilhermesan.peixeurbanooffers.di

import com.guilhermesan.peixeurbanooffers.OffersApp
import com.guilhermesan.peixeurbanooffers.ui.screens.offers.OffersViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [AppModule::class, AndroidSupportInjectionModule::class, BuildersModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: OffersApp): Builder
        fun build(): AppComponent

    }

    fun inject(app: OffersApp)
    fun inject(offersViewModel: OffersViewModel)
}

