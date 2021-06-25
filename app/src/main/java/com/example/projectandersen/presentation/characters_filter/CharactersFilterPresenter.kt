package com.example.projectandersen.presentation.characters_filter

import com.example.projectandersen.R
import com.example.projectandersen.presentation.utils.Constants.CHARACTERS_GENDER_FILTER_FEMALE
import com.example.projectandersen.presentation.utils.Constants.CHARACTERS_GENDER_FILTER_GENDERLESS
import com.example.projectandersen.presentation.utils.Constants.CHARACTERS_GENDER_FILTER_MALE
import com.example.projectandersen.presentation.utils.Constants.CHARACTERS_GENDER_FILTER_UNKNOWN
import com.example.projectandersen.presentation.utils.Constants.CHARACTERS_STATUS_FILTER_ALIVE
import com.example.projectandersen.presentation.utils.Constants.CHARACTERS_STATUS_FILTER_DEAD
import com.example.projectandersen.presentation.utils.Constants.CHARACTERS_STATUS_FILTER_UNKNOWN
import moxy.MvpPresenter

private const val NON_SELECTED_ID = -1

class CharactersFilterPresenter : MvpPresenter<ICharactersFilterView>() {

    fun setStatusSelected(status: String) {
        val id = when (status) {
            CHARACTERS_STATUS_FILTER_ALIVE -> R.id.radioStatusAlive
            CHARACTERS_STATUS_FILTER_DEAD -> R.id.radioStatusDead
            CHARACTERS_STATUS_FILTER_UNKNOWN -> R.id.radioStatusUnknown
            else -> null
        }
        viewState.onSelectStatusFilter(id)
    }

    fun setGenderSelected(status: String) {
        val id = when (status) {
            CHARACTERS_GENDER_FILTER_FEMALE -> R.id.radioGenderFemale
            CHARACTERS_GENDER_FILTER_MALE -> R.id.radioGenderMale
            CHARACTERS_GENDER_FILTER_GENDERLESS -> R.id.radioGenderGenderless
            CHARACTERS_GENDER_FILTER_UNKNOWN -> R.id.radioGenderUnknown
            else -> null
        }
        viewState.onSelectGenderFilter(id)
    }

    fun applyFilters(
        filterGenderId: Int,
        filterStatusId: Int,
        filterName: String,
        filterSpecies: String,
        filterType: String
    ) {

        val filterGender = when (filterGenderId) {
            R.id.radioGenderFemale -> CHARACTERS_GENDER_FILTER_FEMALE
            R.id.radioGenderMale -> CHARACTERS_GENDER_FILTER_MALE
            R.id.radioGenderGenderless -> CHARACTERS_GENDER_FILTER_GENDERLESS
            R.id.radioGenderUnknown -> CHARACTERS_GENDER_FILTER_UNKNOWN
            else -> null
        }

        val filterStatus = when (filterStatusId) {
            R.id.radioStatusAlive -> CHARACTERS_STATUS_FILTER_ALIVE
            R.id.radioStatusDead -> CHARACTERS_STATUS_FILTER_DEAD
            R.id.radioStatusUnknown -> CHARACTERS_STATUS_FILTER_UNKNOWN
            else -> null
        }

        val charactersFilterData = CharactersFilterData(
            filterStatus = filterStatus,
            filterGender = filterGender,
            filterName = if (filterName.isNotBlank()) filterName else null,
            filterSpecies = if (filterSpecies.isNotBlank()) filterSpecies else null,
            filterType = if (filterType.isNotBlank()) filterType else null,
        )
        viewState.onFilterApplied(charactersFilterData)
    }
}