package com.example.cpt_odos_diary.retrofit

import com.google.gson.annotations.SerializedName

data class PostResPlantChoice (
    @SerializedName("status")            val status: Int,
    @SerializedName("success")           val success : Boolean,
    @SerializedName("message")           val message: String,
    @SerializedName("data")              val data: List<RaisePlantList>
)

data class RaisePlantList(
    @SerializedName("user")         val uid:String,
    @SerializedName("plant_name")   val plantName:String,
    @SerializedName("save_date")    val savedate: String,
    @SerializedName("plant_state")  val plantstate: String,
    @SerializedName("count")        val count: String,
    @SerializedName("is_Activate")  val isActivate: String,
    @SerializedName("podSkins")     val podSkin: String,
    @SerializedName("backSkins")    val backSkin: String,
    @SerializedName("level")        val level: String
)
