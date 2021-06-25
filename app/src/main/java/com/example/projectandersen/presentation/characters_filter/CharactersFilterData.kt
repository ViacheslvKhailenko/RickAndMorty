package com.example.projectandersen.presentation.characters_filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharactersFilterData(
    val filterGender: String? = null,
    val filterStatus: String? = null,
    val filterName: String? = null,
    val filterSpecies: String? = null,
    val filterType: String? = null
) : Parcelable {
    fun isFilterExist(): Boolean {
        return filterGender != null ||
                filterSpecies != null ||
                filterType != null ||
                filterStatus != null ||
                filterName != null
    }
}