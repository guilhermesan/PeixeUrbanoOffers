package com.guilhermesan.peixeurbanooffers.ui.screens.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import dagger.android.AndroidInjection

abstract class BaseActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    fun onCreated(){
        setup()
        subscribe()
    }

    fun setupToolBar(toolbar: Toolbar, title:String, showBack:Boolean) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(showBack)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.title = title
    }

    abstract fun setup()
    abstract fun subscribe()
}