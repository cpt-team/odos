package com.example.cpt_odos_diary.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApi {

    @Headers("Content-Type: application/json")
    @POST("/user/signup")
    fun postSignup(

        @Body requestData: PostReqSignUp

    ): Call<PostResSignUp>


    @Headers("Content-Type: application/json")  //@Headers 어노테이션 이용해 헤더값 넣어주기
    @POST("user/signin")                         //HTTP 메소드를 설정해주고 API와 URL 작성
    fun postSignIn(
        //@Body 어노테이션을 통해 RequestBody 데이터를 넣어준다.
        @Body postReqSignIn: PostReqSignIn
    ) : Call<PostResSignIn>
}