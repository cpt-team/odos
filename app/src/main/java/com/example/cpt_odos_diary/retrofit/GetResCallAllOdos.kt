package com.example.cpt_odos_diary.retrofit

import com.google.gson.annotations.SerializedName

data class GetResCallAllOdos (
    @SerializedName("status") val status : Int,
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : List<OdosList>

)

data class OdosList(
    @SerializedName("content") val content : String,
    @SerializedName("createAt") val createAt : String,
    @SerializedName("emotion") val emotion : String,
    @SerializedName("whether") val whether : String,
)
