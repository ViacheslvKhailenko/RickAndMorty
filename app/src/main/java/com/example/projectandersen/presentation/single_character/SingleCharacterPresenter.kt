package com.example.projectandersen.presentation.single_character

import com.example.projectandersen.data.apiservice.NetworkConnectionRetrofit2
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import java.lang.Exception

const val FIRST_PAGE = 1

class SingleCharacterPresenter : MvpPresenter<ISingleCharacterView>() {

    fun getSingleCharacter(id: Int) {
        presenterScope.launch {
            try {
                val result = NetworkConnectionRetrofit2
                    .retrofitCharactersAPI
                    .getSingleCharacter(id)
                getAllEpisodes(result.episode)
                viewState.onSuccessGetData(result)
            } catch (e: Exception) {
                viewState.onErrorGetData(e)
            }
        }
    }


    fun getAllEpisodes(list: List<String>) {
        presenterScope.launch {
            try {
                viewState.onStartLoadingData()

                val results = list.map {
                    async {
                        NetworkConnectionRetrofit2
                            .retrofitEpisodesAPI
                            .getSingleEpisodeByUrl(it)
                    }
                }.awaitAll()
                viewState.onSuccessGetEpisodes(results)
            } catch (e: Exception) {
                viewState.onErrorGetData(e)
            }
        }
    }
}