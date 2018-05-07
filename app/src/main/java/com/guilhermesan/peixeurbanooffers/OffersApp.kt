package com.guilhermesan.peixeurbanooffers

import android.app.Activity
import android.app.Application
import com.guilhermesan.peixeurbanooffers.di.AppComponent
import com.guilhermesan.peixeurbanooffers.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class OffersApp : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    /** Returns a [DispatchingAndroidInjector] of [Activity]s.  */
    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    lateinit var appComponent:AppComponent

    override fun onCreate() {
        super.onCreate()
        //Init Dagger
        appComponent =DaggerAppComponent
                .builder()
                .application(this)
                .build()
        appComponent.inject(this)

    }

}