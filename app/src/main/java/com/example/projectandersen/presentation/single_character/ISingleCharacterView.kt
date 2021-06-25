package com.example.projectandersen.presentation.single_character

import com.example.projectandersen.data.models.SingleCharacter
import com.example.projectandersen.data.models.SingleEpisode
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ISingleCharacterView : MvpView {
    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onSuccessGetData(character: SingleCharacter)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onStartLoadingData()

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onSuccessGetEpisodes(episodes: List<SingleEpisode>)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onErrorGetData(throwable: Throwable)
}