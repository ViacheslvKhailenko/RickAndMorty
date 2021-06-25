package com.example.projectandersen.presentation.single_location

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
import com.example.projectandersen.data.models.SingleLocation
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class SingleLocationFragment : MvpAppCompatFragment(), ISingleLocationView {

    private val presenter by moxyPresenter { SingleLocationPresenter() }

    lateinit var recyclerView: RecyclerView
    val adapter = SingleLocationAdapter()
    lateinit var locationName: TextView
    lateinit var locationType: TextView
    lateinit var locationDimension: TextView
    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.single_location_fragment, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewSingleLocationCharacters)
        locationName = view.findViewById(R.id.singleLocationName)
        locationType = view.findViewById(R.id.singleLocationType)
        locationDimension = view.findViewById(R.id.singleLocationDimension)
        progressBar = view.findViewById(R.id.progressBarSingleLocation)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getSingleLocation(requireArguments().getInt(ID_EXTRA))
    }

    override fun onSuccessGetData(locations: SingleLocation) {
        locationName.text = locations.name
        locationType.text = locations.type
        locationDimension.text = locations.dimension
    }

    override fun onStartLoadingData() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onSuccessGetCharacters(characters: List<SingleCharacter>) {
        progressBar.visibility = View.INVISIBLE
        adapter.addData(characters)
    }

    override fun onErrorGetData(throwable: Throwable) {
        throwable.message
    }

    companion object {
        const val ID_EXTRA = "ID_EXTRA"

        fun newInstance(
            id: Int
        ) =
            SingleLocationFragment().apply {
                arguments = Bundle().also {
                    it.putInt(ID_EXTRA, id)
                }
            }
    }
}