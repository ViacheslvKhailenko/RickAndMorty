package com.example.projectandersen.presentation.single_episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectandersen.R
import com.example.projectandersen.data.models.SingleCharacter
import com.example.projectandersen.data.models.SingleEpisode
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class SingleEpisodeFragment : MvpAppCompatFragment(), ISingleEpisodeView {

    private val presenter by moxyPresenter { SingleEpisodePresenter() }

    lateinit var recyclerView: RecyclerView
    private val adapter = SingleEpisodeAdapter()
    private lateinit var locationName: TextView
    private lateinit var locationAirDate: TextView
    private lateinit var locationEpisodeNumber: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.single_episode_fragment, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewSingleEpisodeCharacters)
        progressBar = view.findViewById(R.id.progressBarSingleEpisode)
        locationName = view.findViewById(R.id.singleEpisodeName)
        locationAirDate = view.findViewById(R.id.singleEpisodeAirDate)
        locationEpisodeNumber = view.findViewById(R.id.singleLocationEpisodeNumber)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getSingleEpisode(requireArguments().getInt(ID_EXTRA))
    }

    override fun onSuccessGetData(episode: SingleEpisode) {
        locationName.text = episode.name
        locationAirDate.text = episode.air_date
        locationEpisodeNumber.text = episode.episode
    }

    override fun onStartLoadingData() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onSuccessGetCharacters(characters: List<SingleCharacter>) {
        progressBar.visibility = View.GONE
        adapter.setData(characters)
    }

    override fun onErrorGetData(throwable: Throwable) {
        throwable.message
    }

    companion object {
        const val ID_EXTRA = "ID_EXTRA"

        fun newInstance(
            id: Int,
        ) =
            SingleEpisodeFragment().apply {
                arguments = Bundle().also {
                    it.putInt(ID_EXTRA, id)
                }
            }
    }
}