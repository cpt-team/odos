package com.example.cpt_odos_diary.retrofit

import com.google.gson.annotations.SerializedName

data class GetResCallAllDiary(
    @SerializedName("status") val status : Int,
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : List<DataList>

)

data class DataList(
    @SerializedName("_id") val did : String,
    @SerializedName("createAt") val createAt : String
)
