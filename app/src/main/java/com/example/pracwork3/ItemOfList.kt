package com.example.pracwork3

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemOfList (

    var title: String,
    var poster_path: String,
    var overview: String,
    var release_date: String,
    var vote_average: Double
): Parcelable