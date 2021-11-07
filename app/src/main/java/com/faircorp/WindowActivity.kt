package com.faircorp


import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.faircorp.model.ApiServices
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WindowActivity : AppCompatActivity() {
    var windowId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val param = intent.getStringExtra(WINDOW_NAME_PARAM)
        val windowName = findViewById<TextView>(R.id.txt_window_name)
        windowName.text = param

        val id = intent.getLongExtra(WINDOW_NAME_PARAM, 0)
        windowId = id

        // getting windows from api
        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().windowsApiService.findById(id).execute(); } // (2)
                    .onSuccess {
                        withContext(context = Dispatchers.Main) {

                            val window = it.body();

                            if (window != null) {
                                findViewById<TextView>(R.id.txt_window_name).text = window.name
                                findViewById<TextView>(R.id.txt_window_status).text = window.status.toString()
                                findViewById<TextView>(R.id.txt_room_name).text = window.room.name
                                findViewById<TextView>(R.id.txt_window_current_temperature).text = window.room.currentTemperature?.toString()
                                findViewById<TextView>(R.id.txt_window_target_temperature).text = window.room.targetTemperature?.toString()
                            }
                        }


                    }
                    .onFailure {
                        withContext(context = Dispatchers.Main) { // (3)
                            Toast.makeText(
                                    applicationContext,
                                    "Error on windows loading $it",
                                    Toast.LENGTH_LONG
                            ).show()
                        }
                    }
        }

    }

    fun switchStatus(view: View) {
        print("debut")
        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().windowsApiService.updateWindow(windowId).execute(); } // (2)
                    .onSuccess {
                        withContext(context = Dispatchers.Main) {
                            finish();
                            startActivity(getIntent());
                        }

                    }
        }
    }
}



