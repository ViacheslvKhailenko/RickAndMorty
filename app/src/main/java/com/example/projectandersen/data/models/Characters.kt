package com.example.projectandersen.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Characters(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("image")
    val image: String
) : Parcelable

@Parcelize
data class CharactersResult(
    @SerializedName("results")
    val results: List<Characters>,
    @SerializedName("info")
    val info: PaginationInfo
) : Parcelable

@Parcelize
data class PaginationInfo(
    @SerializedName("pages")
    val pages: Int
) : Parcelable