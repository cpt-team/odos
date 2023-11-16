package com.example.cpt_odos_diary.retrofit

import com.google.gson.annotations.SerializedName

data class GetResCallDiary (
    @SerializedName("status") val status : Int,
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : List<DiaryList>
)

data class DiaryList(
    @SerializedName("title") val title : String,
    @SerializedName("") val createAt : String
)

