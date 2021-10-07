package com.lalitj.mvvmpractice.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lalitj.mvvmpractice.network.Result
import com.lalitj.mvvmpractice.repository.MainRepository

class MainViewModel : ViewModel() {
//    var communicationList = MutableLiveData<List<RaisedCommunicationResponseDTO>>()

    fun getCommunicationList(): MutableLiveData<Result<Any>> {
        return MainRepository().fetchCommunicationList()
    }

    override fun onCleared() {
        super.onCleared()
    }
}