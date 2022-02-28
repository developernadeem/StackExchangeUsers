package com.proxidize.stackexchangeusers.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BadgeCount(
    val bronze: Int,
    val gold: Int,
    val silver: Int
) : Parcelable