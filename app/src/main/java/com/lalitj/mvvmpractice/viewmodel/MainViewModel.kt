package com.lalitj.mvvmpractice.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lalitj.mvvmpractice.models.RaisedCommunicationResponseDTO
import com.lalitj.mvvmpractice.network.AppAPIHelper
import com.lalitj.mvvmpractice.network.Result
import kotlinx.coroutines.launch
import org.altruist.BajajExperia.Models.CommunicationRequestListDTO
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val apiHelper: AppAPIHelper
) : ViewModel() {
    var communicationData = MutableLiveData<Result<Any>>()
    fun fetchList() {
        viewModelScope.launch {
            communicationData.postValue(Result.Loading(true))
            apiHelper.fetchCommunicationsSMS(CommunicationRequestListDTO(customerId = "9194171"))
                .also {
                    communicationData.postValue(it)
                }
        }
    }
}