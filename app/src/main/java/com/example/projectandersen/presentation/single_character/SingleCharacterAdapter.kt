package com.example.projectandersen.presentation.single_character

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectandersen.R
import com.example.projectandersen.data.models.SingleEpisode

class SingleCharacterAdapter :
    RecyclerView.Adapter<SingleCharacterAdapter.SingleCharacterViewHolder>() {

    private val listSingleCharacterEpisodes = mutableListOf<SingleEpisode>()

    fun setData(episode: List<SingleEpisode>) {
        listSingleCharacterEpisodes.clear()
        listSingleCharacterEpisodes.addAll(episode)
        notifyDataSetChanged()
    }

    fun addData(episode: List<SingleEpisode>) {
        listSingleCharacterEpisodes.addAll(episode)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SingleCharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.episodes_item, parent, false)
        return SingleCharacterViewHolder(view)
    }

    override fun getItemCount() = listSingleCharacterEpisodes.size

    override fun onBindViewHolder(holder: SingleCharacterViewHolder, position: Int) {
        val item = listSingleCharacterEpisodes[position]
        holder.bind(item)
    }

    class SingleCharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.episodesName)
        private val number = itemView.findViewById<TextView>(R.id.episodesNumber)
        private val date = itemView.findViewById<TextView>(R.id.episodesDate)

        fun bind(episode: SingleEpisode) {
            name.text = episode.name
            number.text = episode.episode
            date.text = episode.air_date
        }
    }
}