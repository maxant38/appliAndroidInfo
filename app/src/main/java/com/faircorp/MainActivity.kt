package com.faircorp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.faircorp.model.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// activité principale - ecran d'accueil de l'application

const val WINDOW_NAME_PARAM = "com.faircorp.windowname.attribute"

class MainActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // je fais un appel asynchrone à l'api meteo pour obtenir des informations en directes sur la méteo de Saint-Etienne
        lifecycleScope.launch(context = Dispatchers.IO) {
            runCatching { ApiServices().meteoApiService.getMeteo().execute(); }
                .onSuccess {
                    withContext(context = Dispatchers.Main) {
                        var meteoResponse = it.body()

                        if (meteoResponse != null) {
                            findViewById<TextView>(R.id.meteoTemperature).text = meteoResponse.main.temp.toString() //je place les informations récupérer dans les différents Textview
                            findViewById<TextView>(R.id.meteoHumidity).text = meteoResponse.main.humidity.toString()
                            findViewById<TextView>(R.id.meteoMain).text = meteoResponse.weather[0].main
                            findViewById<TextView>(R.id.meteoIcon).text = meteoResponse.weather[0].icon
                        }

                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) {// je gère le cas où l'appel de l'api fail
                        Toast.makeText(
                            applicationContext,
                            "Error on meteo loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

    }

    /** Je paramètre la fonction du bouton qui va permettre à l'utilisateur de faire une recherche de window grâce à son id */
    fun openWindow(view: View) {
        val getInput = findViewById<EditText>(R.id.inputUser).text.toString()
        val windowId = getInput.toLong()
        val intent = Intent(this, WindowActivity::class.java).apply {
            putExtra(WINDOW_NAME_PARAM, windowId)
        }
        startActivity(intent)
    }

}