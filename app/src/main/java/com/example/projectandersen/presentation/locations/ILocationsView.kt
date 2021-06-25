package com.example.projectandersen.presentation.locations

import com.example.projectandersen.data.models.Locations
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ILocationsView : MvpView {
    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onSuccessGetLocations(locations: List<Locations>)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onSuccessUpdateLocations(locations: List<Locations>)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onErrorGetLocations(throwable: Throwable)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onShowProgress()

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onHideProgress()

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onClickLocationsFilter()
}