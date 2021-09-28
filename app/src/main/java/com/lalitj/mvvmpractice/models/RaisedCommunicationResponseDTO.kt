package com.lalitj.mvvmpractice.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RaisedCommunicationResponseDTO(
    @SerializedName("SMStext")
    var SMStext: String? = null,
    @SerializedName("eventCaptureDate")
    var eventCaptureDate: String? = null,
    @SerializedName("communicationType")
    var communicationType: String? = null
) : Parcelable
