package com.example.cpt_odos_diary.retrofit

import com.google.gson.annotations.SerializedName

class GetResAllPlants (
    @SerializedName("status")            val status: Int,
    @SerializedName("success")           val success : Boolean,
    @SerializedName("message")           val message: String,
    @SerializedName("data")              val data: List<PlantList>
)
data class PlantList(
    @SerializedName("plant_name")        val plantName: String,
    @SerializedName("floriography")      val floriography: String,
    @SerializedName("plant_desc")        val plantDesc: String
)

