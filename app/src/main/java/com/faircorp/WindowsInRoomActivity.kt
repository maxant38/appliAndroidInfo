package com.faircorp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faircorp.model.OnWindowSelectedListener

//Activité qui va afficher l'ensemble des windows pour une room en particulier

class WindowsInRoomActivity : AppCompatActivity(){
    var windowId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_windows_in_room)





    }
}