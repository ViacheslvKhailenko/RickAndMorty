package com.example.projectandersen.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.projectandersen.R
import com.example.projectandersen.data.Episodes
import com.example.projectandersen.presentation.episodes.EpisodesAdapter
import com.example.projectandersen.presentation.episodes.EpisodesPresenter
import com.example.projectandersen.presentation.episodes.IEpisodesView
import com.example.projectandersen.presentation.episodes_filter.EpisodesFilterFragment
import com.example.projectandersen.presentation.single_episode.SingleEpisodeFragment
import com.example.projectandersen.presentation.utils.InternetUtils
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.inject

class EpisodesFragment : MvpAppCompatFragment(), IEpisodesView {

    private val episodesPresenter: EpisodesPresenter by inject()
    private val presenter by moxyPresenter { episodesPresenter }

    private val clickListener: (episodes: Episodes) -> Int = {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        val singleEpisodeFragment = SingleEpisodeFragment.newInstance(
            it.id
        )
        transaction.replace(R.id.mainFragment, singleEpisodeFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    lateinit var swipeRecyclerView: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView
    val adapter = EpisodesAdapter(clickListener)
    lateinit var progressBar: ProgressBar
    lateinit var searchView: SearchView
    lateinit var imageButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.episodes_fragment, container, false)
        searchView = view.findViewById(R.id.searchEpisodes)
        swipeRecyclerView = view.findViewById(R.id.allEpisodesSwipeRefreshLayout)
        imageButton = view.findViewById(R.id.episodesFilterBtn)
        recyclerView = view.findViewById(R.id.recyclerViewEpisodes)
        progressBar = view.findViewById(R.id.progressBarEpisodes)
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
        swipeRecyclerView.setOnRefreshListener {
            presenter.getAllEpisodes(InternetUtils.isInternetAvailable(requireContext()))
            adapter.notifyDataSetChanged()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    presenter.doOnSearch(it)
                    return true
                } ?: run {
                    presenter.doOnSearch("")
                    return false
                }
            }
        })
        imageButton.setOnClickListener { onClickEpisodesFilter() }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getAllEpisodes(InternetUtils.isInternetAvailable(requireContext()))
    }

    override fun onSuccessGetEpisodes(episodes: List<Episodes>) {
        adapter.setData(episodes)
        swipeRecyclerView.isRefreshing = false
    }

    override fun onSuccessUpdateEpisodes(episodes: List<Episodes>) {
        adapter.addData(episodes)
    }

    override fun onErrorGetEpisodes(throwable: Throwable) {
        throwable.message
        Toast.makeText(context, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun onShowProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onHideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun onSearchUpdateList(filteredEpisodes: List<Episodes>) {
        adapter.setData(filteredEpisodes)
    }

    override fun onClickEpisodesFilter() {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        val filterEpisodesFragment = EpisodesFilterFragment.newInstance()
        transaction.replace(R.id.mainFragment, filterEpisodesFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    companion object {
        fun newInstance() = EpisodesFragment()
    }
}