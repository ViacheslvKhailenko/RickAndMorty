package com.example.projectandersen.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodesPagination(
    @SerializedName("count")
    val count: Int,
    @SerializedName("pages")
    val pages: Int
) : Parcelable

@Entity
@Parcelize
data class Episodes(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("air_date")
    val air_date: String,
    @SerializedName("episode")
    val episode: String
) : Parcelable

@Parcelize
data class EpisodesResult(
    @SerializedName("info")
    val info: EpisodesPagination,
    @SerializedName("results")
    val results: List<Episodes>
) : Parcelable