package com.faircorp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView




class WindowActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        val id = intent.getLongExtra(WINDOW_NAME_PARAM, 0)

        val windowService = WindowService()
        val param = intent.getStringExtra(WINDOW_NAME_PARAM)
        // val windowName = findViewById<TextView>(R.id.inputUser)
        // windowName.text = param ?: id.toString()
        val window = windowService.findById(id)

        if (window != null) {
            findViewById<TextView>(R.id.txt_window_name).text = window.name
            findViewById<TextView>(R.id.txt_room_name).text = window.room.name
            findViewById<TextView>(R.id.txt_window_current_temperature).text = window.room.currentTemperature?.toString()
            findViewById<TextView>(R.id.txt_window_target_temperature).text = window.room.targetTemperature?.toString()
            findViewById<TextView>(R.id.txt_window_status).text = window.status.toString()
        }





    }



}