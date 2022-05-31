package com.lalitj.mvvmpractice.network

import com.lalitj.mvvmpractice.models.RaisedCommunicationResponseDTO
import org.altruist.BajajExperia.Models.CommunicationRequestListDTO
import org.altruist.BajajExperia.Models.SMSCommunicationDTO
import java.text.SimpleDateFormat
import javax.inject.Inject

class AppAPIHelper @Inject constructor(
    private val apiService: APIInterface
) : BaseRepository(), CoroutineAPIService {

    override suspend fun fetchCommunicationsSMS(communicationRequestListDTO: CommunicationRequestListDTO): Result<SMSCommunicationDTO> =
        makeApiCall {
            apiService.getCommunicationList(communicationRequestListDTO)
        }.run {
            when (this) {
                is Result.Success -> {
                    when {
                        this.data.errorCode.equals("00") -> {
                            val COMMUNICATION_TYPE_TRANSACTION = "transaction"
                            val COMMUNICATION_TYPE_PROMOTIONAL = "promotional"
                            var requestList = mutableListOf<RaisedCommunicationResponseDTO>()
                            val transcationData =
                                this.data.transData as MutableList<RaisedCommunicationResponseDTO>
                            val promData =
                                this.data.promoData as MutableList<RaisedCommunicationResponseDTO>
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
                            data.finalList = requestList
                            Result.Success(data)
                        }
                        else -> Result.Error(data.errorDesc, true)
                    }
                }
                else -> this
            }
        }
}
