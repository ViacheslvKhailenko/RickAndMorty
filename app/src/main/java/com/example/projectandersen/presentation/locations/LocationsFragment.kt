package com.example.projectandersen.presentation.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.projectandersen.R
import com.example.projectandersen.data.models.Locations
import com.example.projectandersen.presentation.locations_filter.LocationsFilterFragment
import com.example.projectandersen.presentation.single_location.SingleLocationFragment
import com.example.projectandersen.presentation.utils.InternetUtils
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.inject

class LocationsFragment : MvpAppCompatFragment(), ILocationsView {

    private val locationsPresenter: LocationsPresenter by inject()
    private val presenter by moxyPresenter { locationsPresenter }

    private val clickListener: (location: Locations) -> Int = {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        val singleLocationFragment = SingleLocationFragment.newInstance(
            it.id
        )
        transaction.replace(R.id.mainFragment, singleLocationFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    lateinit var swipeRefreshRecyclerView: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    val adapter = LocationsAdapter(clickListener)
    lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.locations_fragment, container, false)
        swipeRefreshRecyclerView = view.findViewById(R.id.allLocationsSwipeRefreshLayout)
        progressBar = view.findViewById(R.id.progressBarLocations)
        imageView = view.findViewById(R.id.locationsFilterBtn)
        recyclerView = view.findViewById(R.id.recyclerViewLocations)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                presenter.doOnScroll(
                    InternetUtils.isInternetAvailable(requireContext()),
                    totalItemCount,
                    lastVisibleItemPosition
                )
            }
        })
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter
        swipeRefreshRecyclerView.setOnRefreshListener {
            presenter.getAllLocations(InternetUtils.isInternetAvailable(requireContext()))
            adapter.notifyDataSetChanged()
        }
        imageView.setOnClickListener { onClickLocationsFilter() }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getAllLocations(InternetUtils.isInternetAvailable(requireContext()))
    }

    override fun onSuccessGetLocations(locations: List<Locations>) {
        adapter.setData(locations)
        swipeRefreshRecyclerView.isRefreshing = false
    }

    override fun onSuccessUpdateLocations(locations: List<Locations>) {
        adapter.addData(locations)
    }

    override fun onErrorGetLocations(throwable: Throwable) {
        throwable.message
    }

    override fun onShowProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onHideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun onClickLocationsFilter() {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        val filterLocationFragment = LocationsFilterFragment.newInstance()
        transaction.replace(R.id.mainFragment, filterLocationFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    companion object {
        fun newInstance() = LocationsFragment()
    }
}