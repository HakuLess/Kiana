package com.haku.kiana

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val a = true

    private var c = 300000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            click(view)
        }
    }

    fun click(view: View) {
        clickView(view)
//        this.clickViewHack(view)
//        clickViewHack(view)
    }

//    fun clickViewHack(view: View) {
//        Snackbar.make(view, "hack success", Snackbar.LENGTH_LONG)
//            .setAction("Action", null).show()
//    }

//    fun clickView(view: View) {
//        Snackbar.make(view, "$c", Snackbar.LENGTH_LONG)
//            .setAction("Action", null).show()
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

fun clickView(view: View) {
    Snackbar.make(view, "hack success 0000", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show()
}