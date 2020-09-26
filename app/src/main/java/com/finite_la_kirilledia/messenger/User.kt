package com.finite_la_kirilledia.messenger

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val uid: String = "",
    val username: String = ""
) : Parcelable