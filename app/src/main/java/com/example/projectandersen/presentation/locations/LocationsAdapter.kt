package com.example.projectandersen.presentation.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectandersen.R
import com.example.projectandersen.data.models.Locations

class LocationsAdapter(
    private val clickListener: (location: Locations) -> Int
) : RecyclerView.Adapter<LocationsAdapter.LocationsViewHolder>() {

    var listLocations = mutableListOf<Locations>()

    fun setData(list: List<Locations>) {
        listLocations.clear()
        listLocations.addAll(list)
        notifyDataSetChanged()
    }

    fun addData(list: List<Locations>) {
        listLocations.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.locations_item, parent, false
            )
        return LocationsViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        val item = listLocations[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            clickListener(item)
        }
    }

    override fun getItemCount() = listLocations.size

    class LocationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.locationName)
        private val type = itemView.findViewById<TextView>(R.id.locationType)
        private val dimension = itemView.findViewById<TextView>(R.id.locationDimension)

        fun bind(location: Locations) {
            name.text = location.name
            type.text = location.type
            dimension.text = location.dimension
        }
    }
}