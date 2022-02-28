package com.proxidize.stackexchangeusers.features.details

import androidx.lifecycle.*
import com.proxidize.stackexchangeusers.data.StackExchangeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repo: StackExchangeRepository) :
    ViewModel() {
    private val _userId = MutableLiveData<Int>()
    val topTags = _userId.distinctUntilChanged().switchMap {
        repo.topTags(it).asLiveData()
    }

    fun serUserId(userId: Int) {
        _userId.postValue(userId)
    }
}