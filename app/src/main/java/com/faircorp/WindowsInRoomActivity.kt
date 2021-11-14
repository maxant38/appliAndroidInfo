package com.faircorp

import WindowAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.model.ApiServices
import com.faircorp.model.OnWindowSelectedListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//Activité qui va afficher l'ensemble des windows pour une room en particulier

class WindowsInRoomActivity : AppCompatActivity(), OnWindowSelectedListener {
    var windowId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_windows)

        val recyclerView = findViewById<RecyclerView>(R.id.list_windows)
        val adapter = WindowAdapter(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        // Je récupère l'id de la window dont on souhaite avoir plus d'information
        val idRoom = intent.getLongExtra(ROOM_NAME_PARAM, 0)

        //Je fais un appel à l'api pour obtenir l'ensemble des windows
        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().roomsApiService.findWindowsInARoom(idRoom).execute() } // (2)
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
                            "Error on windows loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

    }

    // Je définie l'action à réaliser lorsque l'utilisateur clique sur une window
    override fun onWindowSelected(id: Long) {
        val intent = Intent(this, WindowActivity::class.java).putExtra(WINDOW_NAME_PARAM, id)
        startActivity(intent)
    }

}