package com.guilhermesan.peixeurbanooffers.di

import com.guilhermesan.peixeurbanooffers.OffersApp
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
}

