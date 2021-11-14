package com.faircorp


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.faircorp.model.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Activité qui va afficher l'ensemble des détails d'une room en particulier

class RoomActivity : AppCompatActivity() {

    var idRoomToPass = 0L



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Je récupère dans un premier temps l'id de la room dont on souhaite afficher l'ensemble des informations

        val param = intent.getStringExtra(ROOM_NAME_PARAM)
        val roomName = findViewById<TextView>(R.id.txt_room_name)
        roomName.text = param

        val id = intent.getLongExtra(ROOM_NAME_PARAM, 0)

        idRoomToPass = id

        //Je fais un appel à l'api pour obtenir les informations sur la room en question

        lifecycleScope.launch(context = Dispatchers.IO) {
            runCatching { ApiServices().roomsApiService.findById(id).execute(); }
                .onSuccess {
                    withContext(context = Dispatchers.Main) {
                        var room = it.body();


                        if (room != null) {

                            // Si on a des informations au sujet de la room, je les places dans les textviews prévus à cet effet
                            findViewById<TextView>(R.id.txt_room_name).text = room.name
                            findViewById<TextView>(R.id.room_curent_temp).text =
                                room.currentTemperature.toString()
                            findViewById<TextView>(R.id.room_target_temp).text =
                                room.targetTemperature.toString()

                            val seekBar = findViewById<SeekBar>(R.id.seekBar)

                            Toast.makeText(
                                this@RoomActivity,
                                "Target Temperature " + room?.targetTemperature,
                                Toast.LENGTH_SHORT
                            ).show()

                            //Je parametre la seekbar
                            room?.targetTemperature?.toInt()?.let { seekBar.setProgress(it) }
                            seekBar?.setOnSeekBarChangeListener(object :
                                SeekBar.OnSeekBarChangeListener {
                                override fun onProgressChanged(
                                    seekBar: SeekBar,
                                    progress: Int,
                                    fromUser: Boolean
                                ) {
                                    findViewById<TextView>(R.id.room_target_temp).text =
                                        seekBar.progress.toString()


                                }

                                override fun onStartTrackingTouch(seekBar: SeekBar) {}

                                //Je mets à jour la target temperature selon la valeur de la seekbar

                                override fun onStopTrackingTouch(seekBar: SeekBar) {
                                    Toast.makeText(
                                        this@RoomActivity,
                                        "Target Temperature " + seekBar.progress + "°C",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    room?.targetTemperature = seekBar.progress.toDouble()

                                    //Je fais appel à l'api pour modifier la valeur de currentTemperature
                                    lifecycleScope.launch(context = Dispatchers.IO) {
                                        runCatching {
                                            room?.id?.let {
                                                ApiServices().roomsApiService.updateRoom(
                                                    it,
                                                    room!!
                                                ).execute()
                                            };
                                        }
                                            .onSuccess {
                                                withContext(context = Dispatchers.Main) {
                                                    finish();
                                                    startActivity(getIntent());
                                                }
                                            }
                                            .onFailure {
                                                withContext(context = Dispatchers.Main) {
                                                    Toast.makeText(
                                                        applicationContext,
                                                        "Error during the modification of the temperature",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            }
                                    }

                                }
                            })


                        }
                    }
                }
                // Affiche un message en cas d'erreur de chargement de la room
                .onFailure {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "Error on room loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

        }

    }

    fun checkTheWindows(view: View) {
        val intent = Intent(this, WindowsInRoomActivity::class.java).apply {
            putExtra(ROOM_NAME_PARAM, idRoomToPass)
        }
        startActivity(intent)
    }


}