package com.example.projectandersen.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.projectandersen.R
import com.example.projectandersen.data.models.Characters
import com.example.projectandersen.presentation.characters_filter.CharactersFilterData
import com.example.projectandersen.presentation.characters_filter.CharactersFilterFragment
import com.example.projectandersen.presentation.single_character.SingleCharacterFragment
import com.example.projectandersen.presentation.utils.InternetUtils
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.inject

class CharactersFragment : MvpAppCompatFragment(), ICharactersView {


    private val charactersPresenter: CharactersPresenter by inject()
    private val presenter by moxyPresenter { charactersPresenter }

    private val clickListener: (characters: Characters) -> Int = {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        val singleCharacterFragment = SingleCharacterFragment.newInstance(
            it.id
        )
        transaction.replace(R.id.mainFragment, singleCharacterFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    lateinit var swipeRecyclerView: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView
    lateinit var searchView: SearchView
    private val adapter = CharactersAdapter(clickListener)
    lateinit var progressBar: ProgressBar
    lateinit var imageView: ImageView

    private var charactersFilterData = CharactersFilterData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.characters_fragment, container, false)
        progressBar = view.findViewById(R.id.progressBarCharacters)
        searchView = view.findViewById(R.id.searchCharacters)
        swipeRecyclerView = view.findViewById(R.id.allCharactersSwipeRefreshLayout)
        recyclerView = view.findViewById(R.id.recyclerViewCharacters)
        imageView = view.findViewById(R.id.charactersFilterBtn)
        imageView.setOnClickListener { onClickCharactersFilter() }
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
            presenter.getAllCharacters(InternetUtils.isInternetAvailable(requireContext()))
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
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getAllCharacters(
            isNetworkActive = InternetUtils.isInternetAvailable(
                requireContext()
            ), charactersFilterData = charactersFilterData
        )

        setFragmentResultListener(REQUEST_FILTER_CODE) { requestKey, bundle ->
            val charactersFilterData =
                bundle.getParcelable<CharactersFilterData>(CharactersFilterFragment.KEY_FILTER_DATA)
            charactersFilterData?.let {
                this.charactersFilterData = it
                presenter.getAllCharacters(
                    isNetworkActive = InternetUtils.isInternetAvailable(
                        requireContext()
                    ), charactersFilterData = it
                )
            }
        }
    }

    override fun onSuccessGetStartData(characters: List<Characters>) {
        adapter.setData(characters)
        swipeRecyclerView.isRefreshing = false
    }

    override fun onSuccessGetUpdatedData(characters: List<Characters>) {
        adapter.addData(characters)
    }

    override fun onErrorGetData(throwable: Throwable) {
        throwable.message
        Toast.makeText(context, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun onSearchUpdateList(filteredCharacters: List<Characters>) {
        adapter.setData(filteredCharacters)
    }

    override fun onShowProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onHideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun onClickCharactersFilter() {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        val filterCharacterFragment = CharactersFilterFragment.newInstance(charactersFilterData)
        transaction.add(R.id.mainFragment, filterCharacterFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    companion object {
        const val REQUEST_FILTER_CODE = "request_filter_code"
        fun newInstance() = CharactersFragment()
    }
}