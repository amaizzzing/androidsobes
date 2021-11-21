package com.example.sobes.utils

import android.view.View
import kotlin.math.roundToInt

fun Double.getCorrectLatLon(): Double =
    (this * 1000).roundToInt() / 1000.0

fun View.setGone() {
    this.visibility = View.GONE
}

fun View.setVisible() {
    this.visibility = View.VISIBLE
}