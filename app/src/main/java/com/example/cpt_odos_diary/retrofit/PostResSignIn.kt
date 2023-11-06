package com.example.cpt_odos_diary.retrofit

import com.google.gson.annotations.SerializedName

data class PostResSignIn(
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<Datas>
)

data class Datas(
    @SerializedName("_id") val uid : String,
    @SerializedName("pw") val pw : String,
    @SerializedName("token") val token : String
)

