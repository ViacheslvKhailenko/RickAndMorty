package com.example.projectandersen.presentation.single_episode

import com.example.projectandersen.data.models.SingleCharacter
import com.example.projectandersen.data.models.SingleEpisode
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ISingleEpisodeView : MvpView {
    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onSuccessGetData(episode: SingleEpisode)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onStartLoadingData()

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onSuccessGetCharacters(characters: List<SingleCharacter>)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onErrorGetData(throwable: Throwable)
}