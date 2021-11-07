package com.faircorp


import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.faircorp.model.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val param = intent.getStringExtra(ROOM_NAME_PARAM)
        val roomName = findViewById<TextView>(R.id.txt_room_name)
        roomName.text = param

        val id = intent.getLongExtra(ROOM_NAME_PARAM, 0)

        //get request from API
        lifecycleScope.launch(context = Dispatchers.IO) {
            runCatching { ApiServices().roomsApiService.findById(id).execute(); }
                    .onSuccess {
                        withContext(context = Dispatchers.Main) {
                            var room = it.body();

                            if (room != null) {

                                //populate text views
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

                                //setting seekbar value according to the target temperature obtained
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

                                    override fun onStartTrackingTouch(seekBar: SeekBar) {
                                        // Write code to perform some action when touch is started.
                                    }

                                    //updating target temperature from seekbar

                                    override fun onStopTrackingTouch(seekBar: SeekBar) {
                                        // Write code to perform some action when touch is stopped.
                                        Toast.makeText(
                                                this@RoomActivity,
                                                "Target Temperature " + seekBar.progress + "°C",
                                                Toast.LENGTH_SHORT
                                        ).show()
                                        room?.targetTemperature = seekBar.progress.toDouble()

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

                                        }

                                    }
                                })


                            }
                        }
                    }
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

}