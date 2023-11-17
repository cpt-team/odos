package com.example.cpt_odos_diary.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface OdosApi {

    @Headers("Content-Type: application/json")
    @GET("/odos")
    fun getCallAllOdos(
        @Query("uid") uid: String,
        @Query("year") year : String,
        @Query("month") month : String
    ) : Call<GetResCallAllOdos>


    @Headers("Content-Type: application/json")  //@Headers 어노테이션 이용해 헤더값 넣어주기
    @POST("/odos")                         //HTTP 메소드를 설정해주고 API와 URL 작성
    fun postCreateOdos(
        // @Body 어노테이션을 통해 RequestBody 데이터를 넣어준다.
        // PostReqSignIn 객체를 넣으면 PostResSignIn 데이터를 받겠다.

        @Body postReqCreateOdos: PostReqCreateOdos
    ) : Call<PostResCreateOdos>
}