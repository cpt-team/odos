package com.example.cpt_odos_diary.retrofit

import com.google.gson.annotations.SerializedName

data class PostResSignUp (
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message: String,
)