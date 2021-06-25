package com.example.projectandersen.presentation.characters

import com.example.projectandersen.data.models.Characters
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ICharactersView : MvpView {
    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onSuccessGetStartData(characters: List<Characters>)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onSuccessGetUpdatedData(characters: List<Characters>)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onErrorGetData(throwable: Throwable)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onSearchUpdateList(filteredCharacters: List<Characters>)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onShowProgress()

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onHideProgress()

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onClickCharactersFilter()
}