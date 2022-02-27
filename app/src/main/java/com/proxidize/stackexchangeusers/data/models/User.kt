package com.proxidize.stackexchangeusers.data.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("display_name") val displayName: String,
    @SerializedName("profile_image") val profileImage: String,
    @SerializedName("reputation") val reputation: Int,
    @SerializedName("location") val location: String?,
    @SerializedName("creation_date") val creationDate: Long,
    @SerializedName("badge_counts") val badgeCount:BadgeCount

)