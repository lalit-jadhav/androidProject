package com.lalitj.mvvmpractice.network

import org.altruist.BajajExperia.Models.CommunicationRequestListDTO
import org.altruist.BajajExperia.Models.SMSCommunicationDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIInterface {
    @POST("SMSCommunication")
    suspend fun getCommunicationList(@Body communicationRequestListDTO: CommunicationRequestListDTO): Response<SMSCommunicationDTO>
}