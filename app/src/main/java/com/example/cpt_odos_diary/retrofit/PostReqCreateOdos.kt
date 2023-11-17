package com.example.cpt_odos_diary.retrofit

data class PostReqCreateOdos(
    val uid: String,
    val content: String,
    val emotion: String,
    val whether: String
)
