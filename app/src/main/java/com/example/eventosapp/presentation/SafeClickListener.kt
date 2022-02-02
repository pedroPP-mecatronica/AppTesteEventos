package com.example.eventosapp.presentation

import android.os.SystemClock
import android.view.View

class SafeClickListener(
    private var time: Int = 1000,
    private val safeClick: (View) -> Unit
):View.OnClickListener {

    private var lastTimeClicked:Long= 0

    override fun onClick(view: View?) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < time) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        view?.let { safeClick(it) }
    }
}


fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}