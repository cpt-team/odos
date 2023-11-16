package com.example.cpt_odos_diary.retrofit

data class PostReqCreateDiary(
    val uid: String,
    val title: String,
    val content: String,
    val emotion: String,
    val whether: String
)
