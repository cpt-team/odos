package com.example.cpt_odos_diary.retrofit

data class PutReqUpdateDiary (
    val id : String,
    val title : String,
    val content : String,
    val emotion : String,
    val whether : String
)