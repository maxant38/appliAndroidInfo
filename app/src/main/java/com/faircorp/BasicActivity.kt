package com.faircorp


import android.content.Intent
import com.google.android.material.appbar.AppBarLayout
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

// Activité pour avoir mon menu

open class BasicActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_windows -> startActivity(
                Intent(this, WindowsActivity::class.java) //onglet pour afficher l'ensemble des windows
            )

            R.id.menu_rooms -> startActivity(
                    Intent(this, RoomsActivity::class.java) //onglet pour afficher l'ensemble des rooms
            )

            R.id.menu_website -> startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("https://dev-mind.fr")) //onglet pour accéder au site web dev mind
            )
            R.id.menu_email -> startActivity(
                Intent(Intent.ACTION_SENDTO, Uri.parse("mailto://caille.maxence@outlook.fr")) ////onglet pour m'envoyer un mail
            )

        }
        return super.onContextItemSelected(item)
    }
}