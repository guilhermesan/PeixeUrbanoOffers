package com.guilhermesan.datamodule.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.guilhermesan.datacontracts.vos.Offer
import com.guilhermesan.datamodule.api.Api
import com.guilhermesan.datamodule.dataprovider.DataProviderFactoryImp
import com.guilhermesan.datamodule.deserializers.OfferDeserializer
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Component(modules = [ApiModule::class])
interface DataModuleComponent {

    @Component.Builder
    interface Builder {

        fun build(): DataModuleComponent
    }

    fun inject(dataProviderFactoryImp: DataProviderFactoryImp)

}

@Module
class ApiModule {

    @Provides
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
                .registerTypeAdapter(Offer::class.java, OfferDeserializer())
                .create()
    }

    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://api.peixeurbano.com.br/v3/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
    }

    @Provides
    fun providesCoinApi(retrofit: Retrofit): Api {
        return  retrofit.create(Api::class.java)
    }

}