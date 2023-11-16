package com.example.cpt_odos_diary

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cpt_odos_diary.databinding.ActivitySignupBinding
import com.example.cpt_odos_diary.retrofit.LoginApi
import com.example.cpt_odos_diary.retrofit.PostReqSignUp
import com.example.cpt_odos_diary.retrofit.PostResSignUp
import com.example.cpt_odos_diary.retrofit.RetrofitCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SignupActivity : AppCompatActivity(){

    private lateinit var binding : ActivitySignupBinding
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val submitBtn = binding.submitButton

        // 레트로핏 객체
        val signApi : LoginApi = RetrofitCreator.signApi



        submitBtn.setOnClickListener{



            /*
            // Date타입 형식 맞추기
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)
            Log.d(TAG,"시간: ${simpleDateFormat.parse(binding.birthTextviewInputEditText.text.toString()) }")
            */


            // 회원정보를 변수에 넣어서 보낼거에요
            val email : String = binding.emailTextInputEditText.text.toString()
            val pw : String = binding.passwordTextInputEditText.text.toString()
            val name : String = binding.nameTextInputEditText.text.toString()
            val birth : String = binding.birthTextviewInputEditText.text.toString()   // 형식 맞춰서 보내기


            // 서버로 데이터 전송
            val requestData = PostReqSignUp(email, name, pw, birth)
            val callSignUp = requestData.let { it1 -> signApi.postSignup(it1) }

            resultPostSignUp(callSignUp);

            finish()



        }


    }

    private fun resultPostSignUp(callSignUp: Call<PostResSignUp>) {
        callSignUp.enqueue(object: Callback<PostResSignUp> {
            override fun onResponse(
                call: Call<PostResSignUp>,
                response: Response<PostResSignUp>
            ) {
                if(response.body()?.success == true) {
                    Log.d(ContentValues.TAG, "signUp Post 성공 : ${response.body()}")

                }
                else {
                    Log.d(ContentValues.TAG, "signUp Post 실패 : ${response.body()}")

                }

            }

            override fun onFailure(call: Call<PostResSignUp>, t: Throwable) {
                Log.e(ContentValues.TAG, "통신 에러 : $t")

            }

        })

    }



}