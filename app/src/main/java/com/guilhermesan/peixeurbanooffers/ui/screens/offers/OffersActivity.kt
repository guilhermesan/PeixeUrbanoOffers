package com.guilhermesan.peixeurbanooffers.ui.screens.offers

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.IntentSender
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.guilhermesan.peixeurbanooffers.R
import com.guilhermesan.peixeurbanooffers.ui.screens.base.BaseActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_offers.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject




class OffersActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: OffersViewModel

    @Inject
    lateinit var offerAdapter: OfferAdapter

    companion object {
        const val REQUEST_CHECK_SETTINGS = 100

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

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        viewModel.onPermissionGranted()
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                        val dialogPermissionListener = DialogOnDeniedPermissionListener.Builder
                                .withContext(this@OffersActivity)
                                .withTitle("Ops!")
                                .withMessage("Precisamos da sua permissão para mostram as melhores ofertas ;)")
                                .withButtonText(android.R.string.ok)
                                .withIcon(R.mipmap.ic_launcher)
                                .build()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {

                    }

                }).check()

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
}
