package com.proxidize.stackexchangeusers.network

import com.haroldadmin.cnradapter.NetworkResponse
import com.proxidize.stackexchangeusers.data.models.StackExchangeResponse
import com.proxidize.stackexchangeusers.data.models.TopTagResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface StackExchangeService {

    @GET("users?site=stackoverflow")
    suspend fun getUsers(
        @Query("inname") query: String,
        @Query("pagesize") pageSize: Int,
        @Query("order") order: String,
        @Query("sort") sort: String,
    ): SEResponse<StackExchangeResponse>

    @GET("users/{userId}/top-tags?site=stackoverflow")
    suspend fun getTopTags(
        @Path("userId") userId: Int
    ): SEResponse<TopTagResponse>

    companion object {
        const val HOST_NAME = "https://api.stackexchange.com/2.3/"
    }
}
typealias SEResponse<T> = NetworkResponse<T, Any>

enum class Order(val value: String) {
    ASC("asc"),
    DESC("desc")
}

enum class Sort(val value: String) {
    NAME("name"),
    REPUTATION("reputation"),
    CREATION("creation"),
    MODIFIED("modified")

}

