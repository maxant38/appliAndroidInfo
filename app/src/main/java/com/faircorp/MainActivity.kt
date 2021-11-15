package com.faircorp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.faircorp.model.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import kotlin.math.roundToInt

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

                            val humidity = "Humidity:  " + meteoResponse.main.humidity.roundToInt().toString() + " %"
                            val temperature = "Temperature:  "+ (meteoResponse.main.temp - 273.15).roundToInt().toString() + " °C"

                            findViewById<TextView>(R.id.meteoTemperature).text =temperature  //je place les informations récupérer dans les différents Textview
                            findViewById<TextView>(R.id.meteoHumidity).text = humidity
                            findViewById<TextView>(R.id.meteoMain).text = meteoResponse.weather[0].main


                            val imageView = findViewById<ImageView>(R.id.imageView)

                            // Déclarer un exécuteur pour analyser l'URL
                            val executor = Executors.newSingleThreadExecutor()

                            // Une fois que l'exécuteur analyse l'URL et reçoit l'image, le gestionnaire la chargera dans l'imageview
                            val handler = Handler(Looper.getMainLooper())

                            // Initialisation de l'image
                            var image: Bitmap? = null

                            executor.execute {

                                val imageURL = "https://openweathermap.org/img/w/"+meteoResponse.weather[0].icon+".png"

                                // Tente de récupérer l'image et de l'afficher dans l'ImageView avec l'aide du handler
                                try {
                                    val `in` = java.net.URL(imageURL).openStream()
                                    image = BitmapFactory.decodeStream(`in`)

                                    // Seulement pour faire des changements dans l'interface utilisateur
                                    handler.post {
                                        imageView.setImageBitmap(image)
                                    }
                                }

                                // En cas d'échec
                                catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }

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