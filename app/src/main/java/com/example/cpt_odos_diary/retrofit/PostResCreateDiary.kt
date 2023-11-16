package com.example.cpt_odos_diary.retrofit

import com.google.gson.annotations.SerializedName
import java.util.Objects

data class PostResCreateDiary(
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data : List<DiaryId>

)

data class DiaryId(
    @SerializedName("_id") val did : String
)