package com.example.projectandersen.presentation.episodes

import com.example.projectandersen.data.Episodes
import com.example.projectandersen.domain.filter.EpisodesFilterUseCase
import com.example.projectandersen.domain.filter.EpisodesUseCase
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import java.lang.Exception

private const val FIRST_PAGE = 1

class EpisodesPresenter(
    private val episodesUseCase: EpisodesUseCase
) : MvpPresenter<IEpisodesView>() {

    private var page = FIRST_PAGE
    private var allPage = 0
    private var listEpisodes: MutableList<Episodes> = mutableListOf()
    private var currentFilterName: String = ""
    private var nameFilter: String? = null
    private var episodeFilter: String? = null
    val filterUseCase = EpisodesFilterUseCase()

    fun getAllEpisodes(isNetworkActive: Boolean, page: Int = FIRST_PAGE) {
        presenterScope.launch {
            try {
                viewState.onShowProgress()
                val result =
                    if (isFilterExist()) filterUseCase.getEpisodesByFilter(
                        page = page,
                        nameFilter = nameFilter,
                        episodeFilter = episodeFilter
                    ) else episodesUseCase.getEpisodes(isNetworkActive, page)
                if (page == FIRST_PAGE) {
                    allPage = result.info.pages
                    viewState.onHideProgress()
                    viewState.onSuccessGetEpisodes(result.results)
                } else {
                    listEpisodes.addAll(result.results)
                    if (isSearchFilterExist()) {
                        applyFilter()
                    } else {
                        viewState.onHideProgress()
                        viewState.onSuccessUpdateEpisodes(result.results)
                    }
                }
            } catch (e: Exception) {
                viewState.onHideProgress()
                viewState.onErrorGetEpisodes(e)
            }
        }
    }

    fun doOnScroll(isNetworkActive: Boolean, totalItemCount: Int, lastVisibleItemPosition: Int) {
        if (totalItemCount == lastVisibleItemPosition + 1 && page < allPage) {
            page++
            getAllEpisodes(isNetworkActive, page)
        }
    }

    fun doOnSearch(query: String) {
        currentFilterName = query
        if (currentFilterName.isNotBlank()) {
            applyFilter()
        } else {
            viewState.onHideProgress()
            viewState.onSuccessGetEpisodes(listEpisodes)
        }
    }

    private fun applyFilter() {
        val filteredCharacters =
            listEpisodes.filter {
                it.name.contains(
                    currentFilterName,
                    ignoreCase = true
                )
            }
        viewState.onHideProgress()
        viewState.onSearchUpdateList(filteredCharacters)
    }

    private fun isSearchFilterExist(): Boolean {
        return currentFilterName.isNotBlank()
    }

    private fun isFilterExist(): Boolean {
        return nameFilter != null || episodeFilter != null
    }
}