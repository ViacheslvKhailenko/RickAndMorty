package com.example.projectandersen.presentation.single_episode

import com.example.projectandersen.data.apiservice.NetworkConnectionRetrofit2
import com.example.projectandersen.presentation.single_character.FIRST_PAGE
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import java.lang.Exception

const val FIRST_PAGE = 1

class SingleEpisodePresenter : MvpPresenter<ISingleEpisodeView>() {

    fun getSingleEpisode(id: Int) {
        presenterScope.launch {
            try {
                val result = NetworkConnectionRetrofit2
                    .retrofitEpisodesAPI
                    .getSingleEpisode(id)
                getAllCharacters(result.characterList)
                viewState.onSuccessGetData(result)
            } catch (e: Exception) {
                viewState.onErrorGetData(e)
            }
        }
    }

    private fun getAllCharacters(list: List<String>) {
        presenterScope.launch {
            try {
                viewState.onStartLoadingData()
                val results = list.map {
                    async {
                        NetworkConnectionRetrofit2
                            .retrofitCharactersAPI
                            .getSingleCharacterByUrl(it)
                    }
                }.awaitAll()
                viewState.onSuccessGetCharacters(results)
            } catch (e: Exception) {
                viewState.onErrorGetData(e)
            }
        }
    }
}