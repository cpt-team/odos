package com.example.cpt_odos_diary.retrofit

import java.time.LocalDate



data class GetReqCallAllOdos(
    val uid : String,
    val year: String,
    val month: String
)
