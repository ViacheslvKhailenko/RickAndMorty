package com.example.projectandersen.presentation.episodes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectandersen.R
import com.example.projectandersen.data.Episodes

class EpisodesAdapter(
    private val clickListener: (episode: Episodes) -> Int
) : RecyclerView.Adapter<EpisodesAdapter.EpisodesViewHolder>() {

    var listEpisodes = mutableListOf<Episodes>()

    fun setData(list: List<Episodes>) {
        listEpisodes.clear()
        listEpisodes.addAll(list)
        notifyDataSetChanged()
    }

    fun addData(list: List<Episodes>) {
        listEpisodes.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.episodes_item, parent, false
            )
        return EpisodesViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        val item = listEpisodes[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            clickListener(item)
        }
    }

    override fun getItemCount() = listEpisodes.size

    class EpisodesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.episodesName)
        private val episode = itemView.findViewById<TextView>(R.id.episodesNumber)
        private val airDate = itemView.findViewById<TextView>(R.id.episodesDate)

        fun bind(episodes: Episodes) {
            name.text = episodes.name
            episode.text = episodes.episode
            airDate.text = episodes.air_date
        }
    }
}