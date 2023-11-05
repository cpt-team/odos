package com.example.cpt_odos_diary.retrofit

import com.google.gson.annotations.SerializedName

data class PostResSignIn(
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val token: String
)
