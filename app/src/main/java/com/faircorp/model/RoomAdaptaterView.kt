package com.faircorp.model


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.R


class RoomAdapter(val listener: OnRoomSelectedListener) :
        RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    inner class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.window_name)
        val temp: TextView = view.findViewById(R.id.room_current_temp)

    }

    private val items = mutableListOf<RoomDto>()

    fun update(rooms: List<RoomDto>) {
        items.clear()
        items.addAll(rooms)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = items.size // (5)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder { // (6)
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_rooms_item, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {  // (7)
        val room = items[position]
        holder.apply {
            name.text = room.name
            temp.text = room.currentTemperature.toString() + " °C"

            itemView.setOnClickListener { listener.onRoomSelected(room.id) }
        }
    }

    override fun onViewRecycled(holder: RoomViewHolder) { // (2)
        super.onViewRecycled(holder)
        holder.apply {
            itemView.setOnClickListener(null)
        }

    }
}