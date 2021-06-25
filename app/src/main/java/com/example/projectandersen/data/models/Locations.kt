package com.example.projectandersen.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Locations(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("dimension")
    val dimension: String
) : Parcelable

@Parcelize
data class LocationInfo(
    @SerializedName("count")
    val count: Int,
    @SerializedName("pages")
    val pages: Int
) : Parcelable

@Parcelize
data class LocationResult(
    @SerializedName("info")
    val info: LocationInfo,
    @SerializedName("results")
    val results: List<Locations>
) : Parcelable