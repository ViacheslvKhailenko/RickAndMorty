package com.example.projectandersen.presentation.characters

import com.example.projectandersen.data.models.Characters
import com.example.projectandersen.domain.filter.CharactersUseCase
import com.example.projectandersen.presentation.characters_filter.CharactersFilterData
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope

private const val FIRST_PAGE = 1

class CharactersPresenter(
    private val charactersUseCase: CharactersUseCase
) : MvpPresenter<ICharactersView>() {

    private var page = FIRST_PAGE
    private var allPage = 0
    private var listCharacters: MutableList<Characters> = mutableListOf()
    private var currentFilterName: String = ""
    private var charactersFilterData = CharactersFilterData()

    fun getAllCharacters(
        isNetworkActive: Boolean,
        page: Int = FIRST_PAGE,
        charactersFilterData: CharactersFilterData = CharactersFilterData()
    ) {
        this.charactersFilterData = charactersFilterData
        presenterScope.launch {
            try {
                viewState.onShowProgress()
                val result =
                    if (charactersFilterData.isFilterExist()) charactersUseCase.getCharactersByFilter(
                        page = page,
                        genderFilter = charactersFilterData.filterGender,
                        typeFilter = charactersFilterData.filterType,
                        speciesFilter = charactersFilterData.filterSpecies,
                        statusFilter = charactersFilterData.filterStatus,
                        nameFilter = charactersFilterData.filterName
                    ) else charactersUseCase.getCharacters(isNetworkActive, page)
                if (page == FIRST_PAGE) {
                    allPage = result.info.pages
                    listCharacters.clear()
                    listCharacters.addAll(result.results)
                    viewState.onHideProgress()
                    viewState.onSuccessGetStartData(result.results)
                } else {
                    listCharacters.addAll(result.results)
                    if (isSearchFilterExist()) {
                        applyFilter()
                    } else {
                        viewState.onHideProgress()
                        viewState.onSuccessGetUpdatedData(result.results)
                    }
                }
            } catch (e: Exception) {
                viewState.onHideProgress()
                viewState.onErrorGetData(e)
            }
        }
    }

    fun doOnScroll(isNetworkActive: Boolean, totalItemCount: Int, lastVisibleItemPosition: Int) {
        if (totalItemCount == lastVisibleItemPosition + 1 && page < allPage) {
            page++
            getAllCharacters(isNetworkActive, page, charactersFilterData)
        }
    }

    fun doOnSearch(query: String) {
        currentFilterName = query
        if (currentFilterName.isNotBlank()) {
            applyFilter()
        } else {
            viewState.onHideProgress()
            viewState.onSuccessGetStartData(listCharacters)
        }
    }

    private fun applyFilter() {
        val filteredCharacters =
            listCharacters.filter {
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
}

