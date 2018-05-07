package com.guilhermesan.datamodule

import com.guilhermesan.datacontracts.vos.Offer
import com.guilhermesan.datamodule.api.Api
import com.guilhermesan.datamodule.dataprovider.OfferDataProviderImp
import com.guilhermesan.datamodule.di.ApiModule
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */

class DataProviderTest {

    lateinit var apiModule: ApiModule
    lateinit var api:Api
    lateinit var dataProviderImp: OfferDataProviderImp

    var testSubscriberCoinList = TestObserver<List<Offer>>()

    val lat = -23.532887
    val long = -46.791998
    val offset = 0
    val limit = 50


    @Before
    fun setupAPI(){
        apiModule = ApiModule()
        api = apiModule.providesCoinApi(apiModule.provideRetrofit(
                apiModule.provideGson(),apiModule.provideOkHttp()
        ))
        dataProviderImp = OfferDataProviderImp(api, apiModule.provideGson())
    }

    @Test
    fun testGetOffers() {
        dataProviderImp
                .getOffers(lat,long,offset,limit)
                .subscribe(testSubscriberCoinList)
        testSubscriberCoinList.awaitTerminalEvent()
        testSubscriberCoinList.assertNoErrors()
        testSubscriberCoinList.assertComplete()
        testSubscriberCoinList.assertValueCount(1)
        assert(testSubscriberCoinList.values().isNotEmpty())
        var offer = testSubscriberCoinList.values()[0][0]
        Assert.assertThat(offer, CoreMatchers.instanceOf(Offer::class.java))
        Assert.assertNotNull(offer.thumbnailUrl)
        Assert.assertNotNull(offer.title)
        Assert.assertNotNull(offer.description)
        Assert.assertNotNull(offer.price)
    }

}