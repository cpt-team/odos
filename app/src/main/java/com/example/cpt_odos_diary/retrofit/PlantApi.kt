package com.example.cpt_odos_diary.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface PlantApi {

    @Headers("Content-Type: application/json")
    @GET("/plant/plants")
    fun getAllPlant(): Call<GetResAllPlants>

    @Headers("Content-Type: application/json")
    @POST("/plant/plants")
    fun postPlantChoice( // 유저가 식물 선택했을 때
        @Body postReqPlantChoice: PostReqPlantChoice

    ) : Call<PostResPlantChoice>
}