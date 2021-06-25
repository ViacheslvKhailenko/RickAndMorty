package com.example.projectandersen.presentation.single_episode

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectandersen.R
import com.example.projectandersen.data.models.SingleCharacter
import com.squareup.picasso.Picasso

class SingleEpisodeAdapter : RecyclerView.Adapter<SingleEpisodeAdapter.SingleViewHolder>() {

    private val listSingleEpisodeCharacters = mutableListOf<SingleCharacter>()

    fun setData(character: List<SingleCharacter>) {
        listSingleEpisodeCharacters.clear()
        listSingleEpisodeCharacters.addAll(character)
        notifyDataSetChanged()
    }

    fun addData(character: List<SingleCharacter>) {
        listSingleEpisodeCharacters.addAll(character)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SingleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.characters_item, parent, false)
        return SingleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SingleViewHolder, position: Int) {
        val item = listSingleEpisodeCharacters[position]
        holder.bind(item)
    }

    override fun getItemCount() = listSingleEpisodeCharacters.size

    class SingleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.charactersImage)
        private val name = itemView.findViewById<TextView>(R.id.charactersName)
        private val species = itemView.findViewById<TextView>(R.id.charactersSpecies)
        private val status = itemView.findViewById<TextView>(R.id.charactersStatus)
        private val gender = itemView.findViewById<TextView>(R.id.charactersGender)

        fun bind(character: SingleCharacter) {
            Picasso.get().load(character.image).into(image)
            name.text = character.name
            species.text = character.species
            status.text = character.status
            gender.text = character.gender
        }
    }
}