package com.example.cpt_odos_diary

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cpt_odos_diary.App.App
import com.example.cpt_odos_diary.databinding.ActivitySigninBinding
import com.example.cpt_odos_diary.retrofit.PostReqSignIn
import com.example.cpt_odos_diary.retrofit.RetrofitCreator
import com.example.cpt_odos_diary.retrofit.LoginApi
import com.example.cpt_odos_diary.retrofit.PostResSignIn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SigninActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // retrofit 구현
        val btnEvent = binding.subBnt
        val signUpBtn = binding.signUpBtn
        val signApi : LoginApi = RetrofitCreator.signApi

       btnEvent.setOnClickListener{
        val email : String = binding.ETemail.text.toString()
        val pw : String = binding.ETpw.text.toString()
        val requestData = PostReqSignIn(email,pw)

        val callSignIn = signApi.postSignIn(requestData)
        resultPostSignIn(callSignIn);

        App.token_prefs.accessToken?.let { it1 -> Log.d(ContentValues.TAG,it1) }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    signUpBtn.setOnClickListener{
        val intent = Intent(this, SignupActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


}

    private fun resultPostSignIn(callSignIn: Call<PostResSignIn>) {
         callSignIn.enqueue(object: Callback<PostResSignIn>{
             override fun onResponse(
                 call: Call<PostResSignIn>,
                 response: Response<PostResSignIn>
             ) {
                 if(response.isSuccessful) {
                     Log.d(ContentValues.TAG, "signIn Post 성공 : ${response.body()}")
                     Log.d(ContentValues.TAG, "성공 message : ${response.body()?.message}")
                     Log.d(ContentValues.TAG, "성공 status : ${response.body()?.status}")
                     Log.d(ContentValues.TAG, "성공 success : ${response.body()?.success}")
                     App.token_prefs.accessToken = response.body()?.token
                     Log.d(TAG, "성공 token : ${App.token_prefs.accessToken}")// sharedPreference에 데이터 저장 후 호출
                 }
                 else {
                     Log.d(ContentValues.TAG, "signIn Post 실패 : ${response.body()}")
                     Log.d(ContentValues.TAG, "실패 message : ${response.body()?.message}")
                     Log.d(ContentValues.TAG, "실패 status : ${response.body()?.status}")
                     Log.d(ContentValues.TAG, "실패 success : ${response.body()?.success}")
                 }


             }

             override fun onFailure(call: Call<PostResSignIn>, t: Throwable) {
                 Log.d(ContentValues.TAG, "POST 실패 : $t")
             }

         })

    }




}
