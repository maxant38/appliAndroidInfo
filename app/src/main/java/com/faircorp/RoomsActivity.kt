package com.faircorp


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.model.ApiServices
import com.faircorp.model.OnRoomSelectedListener
import com.faircorp.model.RoomAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val ROOM_NAME_PARAM = "com.faircorp.roomname.attribute"

//Activité qui va afficher l'ensemble des rooms

class RoomsActivity : AppCompatActivity(), OnRoomSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView = findViewById<RecyclerView>(R.id.list_rooms) // (2)
        val adapter = RoomAdapter(this) // (3)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        //Je fais un appel à l'api pour obtenir l'ensemble des rooms
        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().roomsApiService.findAll().execute() } // (2)
                    .onSuccess {
                        withContext(context = Dispatchers.Main) { // (3)
                            adapter.update(it.body() ?: emptyList())
                        }
                    }
                    // Je prends en compte le cas où il y a un problème avec la requête à l'api
                    .onFailure {
                        withContext(context = Dispatchers.Main) { // (3)
                            Toast.makeText(
                                    applicationContext,
                                    "Error on rooms loading $it",
                                    Toast.LENGTH_LONG
                            ).show()
                        }
                    }
        }

    }

    // Je définie l'action à réaliser lorsque l'utilisateur clique sur une room
    override fun onRoomSelected(id: Long) {
        val intent = Intent(this, RoomActivity::class.java).putExtra(ROOM_NAME_PARAM, id)
        startActivity(intent)
    }
}