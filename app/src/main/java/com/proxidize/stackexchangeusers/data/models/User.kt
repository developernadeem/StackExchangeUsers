package com.proxidize.stackexchangeusers.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("display_name") val displayName: String,
    @SerializedName("profile_image") val profileImage: String,
    @SerializedName("reputation") val reputation: Int,
    @SerializedName("location") val location: String?,
    @SerializedName("creation_date") val creationDate: Long,
    @SerializedName("badge_counts") val badgeCount: BadgeCount
) : Parcelable