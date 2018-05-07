package com.guilhermesan.datamodule.api

import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("deals")
    fun getOffers(@Query("appSecret") appSecret:String,
                  @Query("lat") latitude:Double,
                  @Query("lng") longitude:Double,
                  @Query("offset") offset:Int,
                  @Query("limit") limit:Int):Observable<JsonObject>

}