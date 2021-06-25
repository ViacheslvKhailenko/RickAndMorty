package com.example.projectandersen.presentation.characters_filter

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ICharactersFilterView : MvpView {
    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onSelectStatusFilter(id: Int?)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onSelectGenderFilter(id: Int?)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onFilterApplied(charactersFilterData: CharactersFilterData)
}