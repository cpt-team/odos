package com.example.cpt_odos_diary.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface DiaryApi {

    @Headers("Content-Type: application/json")
    @GET("/diary")
    fun getCallAllDiary(
        @Query("uid") uid: String,
        @Query("year") year : String,
        @Query("month") month : String
    ) : Call<GetResCallAllDiary>





    @Headers("Content-Type: application/json")  //@Headers 어노테이션 이용해 헤더값 넣어주기
    @POST("/diary")                         //HTTP 메소드를 설정해주고 API와 URL 작성
    fun postCreateDiary(
        // @Body 어노테이션을 통해 RequestBody 데이터를 넣어준다.
        // PostReqSignIn 객체를 넣으면 PostResSignIn 데이터를 받겠다.

        @Body postReqCreateDiary: PostReqCreateDiary
    ) : Call<PostResCreateDiary>





}