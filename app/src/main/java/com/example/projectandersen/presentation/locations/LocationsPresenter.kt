package com.example.projectandersen.presentation.locations

import com.example.projectandersen.domain.filter.LocationsUseCase
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import java.lang.Exception

private const val FIRST_PAGE = 1

class LocationsPresenter(
    private val locationsUseCase: LocationsUseCase
) : MvpPresenter<ILocationsView>() {
    private var page = FIRST_PAGE
    private var allPage = 0

    fun getAllLocations(isNetworkActive: Boolean, page: Int = FIRST_PAGE) {
        presenterScope.launch {
            try {
                viewState.onShowProgress()
                val result = locationsUseCase.getLocations(isNetworkActive, page)
                if (page == FIRST_PAGE) {
                    allPage = result.info.pages
                    viewState.onHideProgress()
                    viewState.onSuccessGetLocations(result.results)
                } else {
                    viewState.onHideProgress()
                    viewState.onSuccessUpdateLocations(result.results)
                }
            } catch (e: Exception) {
                viewState.onHideProgress()
                viewState.onErrorGetLocations(e)
            }
        }
    }


    fun doOnScroll(isNetworkActive: Boolean, totalItemCount: Int, lastVisibleItemPosition: Int) {
        if (totalItemCount == lastVisibleItemPosition + 1 && page < allPage) {
            page++
            getAllLocations(isNetworkActive, page)
        }
    }
}