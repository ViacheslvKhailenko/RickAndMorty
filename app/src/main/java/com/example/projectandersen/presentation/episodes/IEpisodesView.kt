package com.example.projectandersen.presentation.episodes

import com.example.projectandersen.data.Episodes
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType

interface IEpisodesView : MvpView {
    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onSuccessGetEpisodes(episodes: List<Episodes>)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onSuccessUpdateEpisodes(episodes: List<Episodes>)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onErrorGetEpisodes(throwable: Throwable)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onShowProgress()

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onHideProgress()

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onSearchUpdateList(filteredEpisodes: List<Episodes>)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onClickEpisodesFilter()
}