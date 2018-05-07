package com.guilhermesan.peixeurbanooffers.ui.screens.offers

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.guilhermesan.peixeurbanooffers.R
import com.guilhermesan.peixeurbanooffers.ui.screens.base.BaseActivity
import kotlinx.android.synthetic.main.activity_offers.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.view_permission_denied.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject




class OffersActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: OffersViewModel

    @Inject
    lateinit var offerAdapter: OfferAdapter

    companion object {
        const val REQUEST_CHECK_SETTINGS = 100
        const val REQUEST_PERMISSIONS = 200

        fun start(activity: Activity){
            activity.startActivity<OffersActivity>()
            activity.overridePendingTransition(R.anim.apear,R.anim.desapear)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offers)
        onCreated()
    }

    override fun setup() {
        setupToolBar(toolbar,false)
        rvList.layoutManager = LinearLayoutManager(this)
        rvList.adapter = offerAdapter

        checkPermission()
        btPermissionAgain.setOnClickListener({
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSIONS)
        })

    }

    fun checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
               viewModel.permissionDenied()
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_PERMISSIONS)
            }
        } else {
            viewModel.onPermissionGranted()
            permission_container.visibility = View.GONE
        }

    }

    override fun subscribe() {
        with(viewModel){
            isLoading.observe(this@OffersActivity, Observer { isLoading ->
                if (isLoading != null && isLoading) {
                    rvList.visibility = View.GONE
                    loadingView.visibility = View.VISIBLE
                } else {
                    rvList.visibility = View.VISIBLE
                    loadingView.visibility = View.GONE
                }
            })

            offersLiveData.observe(this@OffersActivity, Observer {
                it?.observe(this@OffersActivity, Observer {
                    offerAdapter.submitList(it)
                    viewModel.onListUpdated()
                })
            })

            locationSettingsError.observe(this@OffersActivity, Observer {
                if (it != null) {
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        it.startResolutionForResult(this@OffersActivity,
                                REQUEST_CHECK_SETTINGS)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // Ignore the error.
                    }
                }
            })

            permissionDeniedLiveData.observe(this@OffersActivity, Observer {
                permission_container.visibility = View.VISIBLE
            })
        }

    }


    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSIONS -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    viewModel.onPermissionGranted()
                    permission_container.visibility = View.GONE

                } else {
                   viewModel.permissionDenied()
                }
                return
            }

        }
    }
}
