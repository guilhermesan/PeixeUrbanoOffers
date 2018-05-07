package com.guilhermesan.peixeurbanooffers.ui.screens.offers


import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.location.Location
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.guilhermesan.datacontracts.dataproviders.OfferDataProvider
import com.guilhermesan.datacontracts.vos.Offer
import com.guilhermesan.peixeurbanooffers.datasources.OffersDataSourceFactory
import com.guilhermesan.peixeurbanooffers.ui.screens.base.BaseViewModel
import javax.inject.Inject

@SuppressLint("MissingPermission")
class OffersViewModel(val app: Application) : BaseViewModel(app) {

    @Inject
    lateinit var offerDataProvider: OfferDataProvider

    private var fusedLocationClient: FusedLocationProviderClient? = null

    private val limit = 50


    val permissionDeniedLiveData = MutableLiveData<Boolean>()

    val offersLiveData = MutableLiveData<LiveData<PagedList<Offer>>>()
    lateinit var offersPagedList :LiveData<PagedList<Offer>>

    val locationSettingsError = MutableLiveData<ResolvableApiException>()

    private var lastLocation : Location? = null

    private lateinit var locationRequest :LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var requestingLocationUpdates = false
    private lateinit var offerDataSourceFactory:OffersDataSourceFactory

    init {
        getComponent().inject(this)

    }


    fun onPermissionGranted() {
        showLoading()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(app)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                lastLocation = locationResult.locations[0]
                getOffers()
            }
        }
        createLocationRequest()


    }


    fun permissionDenied(){
        permissionDeniedLiveData.value = true
        hiddeLoading()
    }


    private fun createLocationRequest() {
        locationRequest = LocationRequest().apply {
            interval = 30000
            fastestInterval = 10000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(app)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener { locationSettingsResponse ->
            requestingLocationUpdates = true
            startLocationUpdates()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                locationSettingsError.value = exception
            }
        }

    }

    private fun startLocationUpdates() {
        fusedLocationClient?.requestLocationUpdates(locationRequest,
                locationCallback,
                null /* Looper */)
    }

    private fun getOffers(){
        val location = lastLocation
        if (location != null) {
            offerDataSourceFactory = OffersDataSourceFactory(offerDataProvider, disposeBag, location.latitude, location.longitude)
            val config = PagedList.Config.Builder()
                    .setPageSize(limit)
                    .setInitialLoadSizeHint(limit)
                    .setEnablePlaceholders(false)
                    .build()
            offersPagedList = LivePagedListBuilder<Integer, Offer>(offerDataSourceFactory, config).build()
            offersLiveData.value = offersPagedList
        }
    }

    fun onListUpdated(){
        hiddeLoading()
    }


    fun onResume(){
        if (requestingLocationUpdates) startLocationUpdates()
    }

    fun onPause() {
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient?.removeLocationUpdates(locationCallback)
    }




}