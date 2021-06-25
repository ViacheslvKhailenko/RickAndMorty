package com.example.projectandersen.presentation.single_character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectandersen.R
import com.example.projectandersen.data.models.SingleCharacter
import com.example.projectandersen.data.models.SingleEpisode
import com.squareup.picasso.Picasso
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class SingleCharacterFragment : MvpAppCompatFragment(), ISingleCharacterView {

    private val presenter by moxyPresenter { SingleCharacterPresenter() }

    lateinit var recyclerView: RecyclerView
    private val adapter = SingleCharacterAdapter()
    private lateinit var characterSingleImage: ImageView
    private lateinit var characterSingleName: TextView
    private lateinit var characterSingleGender: TextView
    private lateinit var characterSingleSpecies: TextView
    private lateinit var characterSingleStatus: TextView
    private lateinit var characterSingleOrigin: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.single_character_fragment, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        characterSingleImage = view.findViewById(R.id.characterSingleImage)
        characterSingleName = view.findViewById(R.id.characterSingleName)
        characterSingleGender = view.findViewById(R.id.characterSingleGender)
        characterSingleSpecies = view.findViewById(R.id.characterSingleSpecies)
        characterSingleStatus = view.findViewById(R.id.characterSingleStatus)
        characterSingleOrigin = view.findViewById(R.id.characterSingleOrigin)
        recyclerView = view.findViewById(R.id.recyclerViewSingleCharacterEpisodes)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getSingleCharacter(requireArguments().getInt(ID_EXTRA))
    }

    override fun onSuccessGetData(character: SingleCharacter) {
        Picasso.get().load(character.image).into(characterSingleImage)
        characterSingleName.text = character.name
        characterSingleStatus.text = character.status
        characterSingleGender.text = character.gender
        characterSingleSpecies.text = character.species
        characterSingleOrigin.text = character.origin.name
    }

    override fun onStartLoadingData() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onSuccessGetEpisodes(episodes: List<SingleEpisode>) {
        progressBar.visibility = View.GONE
        adapter.setData(episodes)
    }

    override fun onErrorGetData(throwable: Throwable) {
        throwable.message
    }

    companion object {
        const val ID_EXTRA = "ID_EXTRA"

        fun newInstance(
            id: Int
        ) =
            SingleCharacterFragment().apply {
                arguments = Bundle().also {
                    it.putInt(ID_EXTRA, id)
                }
            }
    }
}