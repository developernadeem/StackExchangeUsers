package com.proxidize.stackexchangeusers.data.models

import com.google.gson.annotations.SerializedName

data class StackExchangeResponse(@SerializedName("items") val items: List<User>)
