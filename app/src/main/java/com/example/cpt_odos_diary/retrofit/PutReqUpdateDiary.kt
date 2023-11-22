package com.example.cpt_odos_diary.retrofit

data class PutReqUpdateDiary (
    val uid : String,
    val createAt : String,
    val title : String,
    val content : String,
    val emotion : String,
    val whether : String
)