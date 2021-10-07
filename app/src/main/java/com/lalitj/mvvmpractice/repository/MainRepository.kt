package com.lalitj.mvvmpractice.repository

import androidx.lifecycle.MutableLiveData
import com.lalitj.mvvmpractice.configs.APIErrorMessage.SESSION_EXPIRED
import com.lalitj.mvvmpractice.configs.APIErrorMessage.UNABLE_TO_PROCESS
import com.lalitj.mvvmpractice.models.RaisedCommunicationResponseDTO
import com.lalitj.mvvmpractice.network.APIClient
import com.lalitj.mvvmpractice.network.APIInterface
import com.lalitj.mvvmpractice.network.Result
import org.altruist.BajajExperia.Models.CommunicationRequestListDTO
import org.altruist.BajajExperia.Models.SMSCommunicationDTO
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat

class MainRepository {
    val COMMUNICATION_TYPE_TRANSACTION = "transaction"
    val COMMUNICATION_TYPE_PROMOTIONAL = "promotional"
    fun fetchCommunicationList(): MutableLiveData<Result<Any>> {
        var communicationList = MutableLiveData<Result<Any>>()
        var requestList = mutableListOf<RaisedCommunicationResponseDTO>()
        APIClient.getClient()?.create(APIInterface::class.java)?.getCommunicationList(
            CommunicationRequestListDTO("9194171")
        )?.enqueue(object : retrofit2.Callback<SMSCommunicationDTO> {
            override fun onResponse(
                call: Call<SMSCommunicationDTO>,
                response: Response<SMSCommunicationDTO>
            ) {
                communicationList.postValue(Result.Loading(false))
                if (response.isSuccessful) {
                    when {
                        response.body()?.errorCode.equals("00") -> {
                            val transcationData =
                                response.body()?.transData as MutableList<RaisedCommunicationResponseDTO>
                            val promData =
                                response.body()?.promoData as MutableList<RaisedCommunicationResponseDTO>
                            for (data in transcationData) {
                                data.communicationType = COMMUNICATION_TYPE_TRANSACTION
                                requestList.add(data)
                            }
                            for (data in promData) {
                                data.communicationType = COMMUNICATION_TYPE_PROMOTIONAL
                                requestList.add(data)
                            }
                            requestList.sortByDescending {
                                SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(
                                    it.eventCaptureDate
                                ).time
                            }
                            communicationList.postValue(Result.Success(data = requestList))
                        }
                        response.body()?.errorCode.equals("107") -> {
                            communicationList.postValue(Result.SessionExpired(SESSION_EXPIRED))
                        }
                        else -> {
                            communicationList.postValue(Result.Error(UNABLE_TO_PROCESS))
                        }
                    }
                } else {
                    communicationList.postValue(Result.Error(UNABLE_TO_PROCESS))
                }
            }

            override fun onFailure(call: Call<SMSCommunicationDTO>, t: Throwable) {
                communicationList.postValue(Result.Error(UNABLE_TO_PROCESS))
                communicationList.postValue(Result.Loading(false))
            }
        })
        return communicationList
    }
}