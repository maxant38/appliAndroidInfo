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

// Activité qui va afficher l'ensemble des détails d'une room en particulier

class WindowActivity : AppCompatActivity() {
    var windowId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Je récupère l'id de la window dont on souhaite avoir plus d'information
        val id = intent.getLongExtra(WINDOW_NAME_PARAM, 0)
        windowId = id

        // Je fais un appel à l'api pour obtenir les informations sur la window en question
        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().windowsApiService.findById(id).execute(); } // (2)
                    .onSuccess {
                        withContext(context = Dispatchers.Main) {

                            val window = it.body();

                            // Si on a des informations au sujet de la window, je les places dans les textviews prévus à cet effet
                            if (window != null) {
                                findViewById<TextView>(R.id.txt_window_name).text = window.name
                                findViewById<TextView>(R.id.txt_window_status).text = window.windowStatus.toString()
                                findViewById<TextView>(R.id.txt_room_name).text = window.roomName
                                findViewById<TextView>(R.id.txt_window_current_temperature).text = window.roomCurrentTemperature?.toString()
                                findViewById<TextView>(R.id.txt_window_target_temperature).text = window.roomTargetTemperature?.toString()
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

    // Je définie la fonction qui sera éxécuter lorsque l'utilisateur appuiera sur le bouton switch
    fun switchStatus(view: View) {

        // Je fais une requête à l'api pour modifier le statut de la window en question
        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().windowsApiService.updateWindow(windowId).execute(); } // (2)
                    .onSuccess {
                        withContext(context = Dispatchers.Main) {
                            finish();
                            startActivity(getIntent());
                        }
                    }

                     .onFailure {
                         withContext(context = Dispatchers.Main) { // (3)
                             Toast.makeText(
                                 applicationContext,
                                 "Error on windows switch statut",
                                 Toast.LENGTH_LONG
                             ).show()
                         }
                     }
        }
    }
}



