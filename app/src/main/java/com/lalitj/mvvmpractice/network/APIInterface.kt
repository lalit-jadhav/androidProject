package com.lalitj.mvvmpractice.network

import org.altruist.BajajExperia.Models.CommunicationRequestListDTO
import org.altruist.BajajExperia.Models.SMSCommunicationDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIInterface {
    @POST("SMSCommunication")
    fun getCommunicationList(@Body communicationRequestListDTO: CommunicationRequestListDTO): Call<SMSCommunicationDTO>
}