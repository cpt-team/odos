package com.example.cpt_odos_diary

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.cpt_odos_diary.App.App
import com.example.cpt_odos_diary.databinding.ActivityDiaryEditBinding
import com.example.cpt_odos_diary.databinding.ActivityOdosEditBinding
import com.example.cpt_odos_diary.retrofit.DiaryApi
import com.example.cpt_odos_diary.retrofit.DiaryId
import com.example.cpt_odos_diary.retrofit.OdosApi
import com.example.cpt_odos_diary.retrofit.PostReqCreateDiary
import com.example.cpt_odos_diary.retrofit.PostReqCreateOdos
import com.example.cpt_odos_diary.retrofit.PostResCreateDiary
import com.example.cpt_odos_diary.retrofit.PostResCreateOdos
import com.example.cpt_odos_diary.retrofit.RetrofitCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OdosEditActivity : AppCompatActivity() {
    lateinit var binding: ActivityOdosEditBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOdosEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val checkBtn = binding.ivCheck
        val backbtn = binding.ivOdosback



        backbtn.setOnClickListener {
            finish()
        }

        checkBtn.setOnClickListener {
            val odosApi: OdosApi = RetrofitCreator.odosApi
            // req /diary에 사용할 데이터 변수 선언
            val uid: String? = App.token_prefs.uid
            val content: String = binding.etContent.text.toString()
            val emotions = binding.rgMood
            val whethers = binding.rgWeather

            val whether: String = when (whethers.checkedRadioButtonId) {
                R.id.RB_sunny -> "sunny"

                R.id.RB_sunnyCloudy -> "sunnyCloudy"
                R.id.RB_windy -> "windy"
                R.id.RB_Cloudy -> "cloudy"
                R.id.RB_rainy -> "rainy"
                R.id.RB_snowy -> "snowy"
                else -> "null"
            }

            val emotion: String = when (emotions.checkedRadioButtonId) {
                R.id.RB_happy -> "happy"
                R.id.RB_love -> "love"
                R.id.RB_sleepy -> "sleepy"
                R.id.RB_sad -> "sad"
                R.id.RB_fine -> "fine"
                R.id.RB_angry -> "angry"
                else -> "null"
            }


            if (uid != null) {
                retrofitPostCreateOdos(odosApi,uid,content,emotion,whether)
            }

            Toast.makeText(applicationContext, "odos를 작성하였습니다!!", Toast.LENGTH_SHORT).show()
        }


    }


    fun retrofitPostCreateOdos(
        odosApi: OdosApi,
        uid: String,
        content: String,
        emotion: String,
        whether: String
    ) {

        val requestData = PostReqCreateOdos(uid, content, emotion, whether)
        val createOdos = odosApi.postCreateOdos(requestData)
        resultPostCreateOdos(createOdos)
    }

    fun resultPostCreateOdos(createOdos: Call<PostResCreateOdos>) {
        createOdos.enqueue(object : Callback<PostResCreateOdos> {
            override fun onResponse(
                call: Call<PostResCreateOdos>,
                response: Response<PostResCreateOdos>
            ) {
                if (response.body()?.success == true) {
                    Log.d(ContentValues.TAG, "/odos post 성공 : ${response.body()}")
                    Log.d(ContentValues.TAG, "/odos data: ${response.body()?.message}")
                    Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_SHORT).show()
                    finish()

                } else {
                    Log.d(ContentValues.TAG, "/odos post 실패 : ${response.body()}")
                    Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_SHORT).show()


                }
            }

            override fun onFailure(call: Call<PostResCreateOdos>, t: Throwable) {
                Log.d(ContentValues.TAG, "/odos post 통신 에러 : $t")
            }

        })
    }
}

