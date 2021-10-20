package com.faircorp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText

const val WINDOW_NAME_PARAM = "com.faircorp.windowname.attribute"

class MainActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /** Called when the user taps the button */
    fun openWindow(view: View) {
        val windowName = findViewById<EditText>(R.id.inputUser).text.toString()

        // Do something in response to button
        val intent = Intent(this, WindowActivity::class.java).apply {
            putExtra(WINDOW_NAME_PARAM, windowName)
        }
        startActivity(intent)
    }








}