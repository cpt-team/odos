package com.example.cpt_odos_diary.retrofit

import java.util.Date

data class PostReqSignUp(
    val email: String,
    val name: String,
    val pw: String,
    val birth: String
)
