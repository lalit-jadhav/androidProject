package org.altruist.BajajExperia.Models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.lalitj.mvvmpractice.models.RaisedCommunicationResponseDTO
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class SMSCommunicationDTO(
    @SerializedName("errorDesc")
    var errorDesc: String? = null,
    @SerializedName("errorCode")
    var errorCode: String? = null,
    @SerializedName("transData")
    var transData: @RawValue List<RaisedCommunicationResponseDTO>? = null,
    @SerializedName("promoData")
    var promoData: @RawValue List<RaisedCommunicationResponseDTO>? = null,
    @SerializedName("finalList")
    var finalList: @RawValue List<RaisedCommunicationResponseDTO>? = null

) : Parcelable
//{
//    constructor(parcel: Parcel) : this(
//        parcel.readString(),
//        parcel.readString()
//    )
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(errorDesc)
//        parcel.writeString(errorCode)
//        parcel.writeTypedList(transData)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<CustomerRequestDTO> {
//        override fun createFromParcel(parcel: Parcel): CustomerRequestDTO {
//            return CustomerRequestDTO(parcel)
//        }
//
//        override fun newArray(size: Int): Array<CustomerRequestDTO?> {
//            return arrayOfNulls(size)
//        }
//    }
//
//
//}
