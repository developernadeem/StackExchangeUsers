package com.proxidize.stackexchangeusers.data

import com.proxidize.stackexchangeusers.data.models.StackExchangeResponse
import com.proxidize.stackexchangeusers.network.Order
import com.proxidize.stackexchangeusers.network.SEResponse
import com.proxidize.stackexchangeusers.network.Sort
import com.proxidize.stackexchangeusers.network.StackExchangeService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * repository class responsible for communication with [StackExchangeService]
 * all the API calls should be access from here*/
class StackExchangeRepository @Inject constructor(private val service: StackExchangeService) {
    fun getUsers(
        query: String,
        pageSize: Int = PAGE_SIZE,
        sort: Sort = Sort.NAME,
        order: Order = Order.ASC
    ) = flow {
        val response = service.getUsers(query, pageSize, order.value, sort.value)
        emit(response)

    }

    fun topTags(userId:Int) = flow {
        val response = service.getTopTags(userId)
        emit(response)
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}