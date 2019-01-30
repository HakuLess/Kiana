package com.haku.kiana.hack

import android.view.View
import androidx.appcompat.app.AlertDialog


/**
 * Usage:
 *
 * Created by HaKu on 2019-01-30.
 */
fun clickViewHack(view: View) {
    AlertDialog.Builder(view.context)
        .setMessage("message")
        .create()
        .show()
//    Snackbar.make(view, "hack success 2222", Snackbar.LENGTH_LONG)
//        .setAction("Action", null).show()
}