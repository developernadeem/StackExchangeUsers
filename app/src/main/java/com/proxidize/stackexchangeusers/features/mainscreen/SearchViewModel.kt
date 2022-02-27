package com.proxidize.stackexchangeusers.features.mainscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.proxidize.stackexchangeusers.data.StackExchangeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: StackExchangeRepository
) : ViewModel() {
    private val _query = MutableLiveData<String>()
    val users = _query.switchMap {
        repo.getUsers(it).asLiveData()
    }

    fun setQuery(q: String) {
        _query.postValue(q)

    }
}