package com.haku.kiana.hack

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.haku.kiana.MainActivity


/**
 * Usage:
 *
 * Created by HaKu on 2019-01-30.
 */
fun clickViewHack(view: View) {
    Snackbar.make(view, "hack success 2222", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show()
}