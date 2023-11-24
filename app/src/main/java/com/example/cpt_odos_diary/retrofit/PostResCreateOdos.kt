package com.example.cpt_odos_diary.retrofit

import com.google.gson.annotations.SerializedName

data class PostResCreateOdos(
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data : List<OdosId>
)

data class OdosId(
    @SerializedName("_id") val oid : String
)


