package org.altruist.BajajExperia.Models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommunicationRequestListDTO(
    @SerializedName("customerId")
    var customerId: String? = null,

    ) : Parcelable




