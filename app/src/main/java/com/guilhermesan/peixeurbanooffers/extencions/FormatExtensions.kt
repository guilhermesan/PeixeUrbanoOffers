package com.guilhermesan.peixeurbanooffers.extencions

import java.text.NumberFormat

fun Double.formatCurrency():String{
    val numberFormat = NumberFormat.getCurrencyInstance()
    return numberFormat.format(this)
}

