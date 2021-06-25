package com.example.projectandersen.presentation.single_location

import com.example.projectandersen.data.models.SingleCharacter
import com.example.projectandersen.data.models.SingleLocation
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ISingleLocationView : MvpView {
    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onSuccessGetData(locations: SingleLocation)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onStartLoadingData()

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onSuccessGetCharacters(characters: List<SingleCharacter>)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun onErrorGetData(throwable: Throwable)
}