package com.example.projectandersen.presentation.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectandersen.R
import com.example.projectandersen.data.models.Characters
import com.squareup.picasso.Picasso

class CharactersAdapter(
    private val clickListener: (characters: Characters) -> Int
) : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {

    var listCharacters = mutableListOf<Characters>()

    fun setData(list: List<Characters>) {
        listCharacters.clear()
        listCharacters.addAll(list)
        notifyDataSetChanged()
    }

    fun addData(list: List<Characters>) {
        listCharacters.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharactersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.characters_item, parent, false)
        return CharactersViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val item = listCharacters[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            clickListener(item)
        }
    }

    override fun getItemCount() = listCharacters.size

    class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.charactersImage)
        private val name = itemView.findViewById<TextView>(R.id.charactersName)
        private val species = itemView.findViewById<TextView>(R.id.charactersSpecies)
        private val status = itemView.findViewById<TextView>(R.id.charactersStatus)
        private val gender = itemView.findViewById<TextView>(R.id.charactersGender)

        fun bind(characters: Characters) {
            Picasso.get().load(characters.image).into(image)
            name.text = characters.name
            species.text = characters.species
            status.text = characters.status
            gender.text = characters.gender
        }
    }
}