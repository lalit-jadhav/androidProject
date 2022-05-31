package com.lalitj.mvvmpractice.network

import org.altruist.BajajExperia.Models.CommunicationRequestListDTO
import org.altruist.BajajExperia.Models.SMSCommunicationDTO
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface CoroutineAPIService {
    @POST("SMSCommunication")
    suspend fun fetchCommunicationsSMS(
        @Body communicationRequestListDTO: CommunicationRequestListDTO
    ): Result<SMSCommunicationDTO>
}