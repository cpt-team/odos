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
        val checkEvent = binding.checkBtn
        val signApi : LoginApi = RetrofitCreator.signApi

       btnEvent.setOnClickListener{
        val email : String = binding.ETemail.text.toString()
        val pw : String = binding.ETpw.text.toString()
        val requestData = PostReqSignIn(email,pw)

        val callSignIn = signApi.postSignIn(requestData)
        ResultPostSignIn(callSignIn);

        App.token_prefs.accessToken?.let { it1 -> Log.d(ContentValues.TAG, it1) }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }
    /*
    checkEvent.setOnClickListener{
        val callGetJwtCheck = App.token_prefs.accessToken?.let { it1 -> jwtApi.getLogin(it1) }

        callGetJwtCheck?.let { it1 -> jwtCheck(it1) }
    }
    */

}

    private fun ResultPostSignIn(callSignIn: Call<PostResSignIn>) {
         callSignIn.enqueue(object: Callback<PostResSignIn>{
             override fun onResponse(
                 call: Call<PostResSignIn>,
                 response: Response<PostResSignIn>
             ) {
                 Log.d(ContentValues.TAG, "성공 : ${response.body()}")
                 Log.d(ContentValues.TAG, "성공 message : ${response.body()?.message}")
                 App.token_prefs.accessToken = response.body()?.token
                 Log.d(TAG, "token : ${App.token_prefs.accessToken}")// sharedPreference에 데이터 저장 후 호출


             }

             override fun onFailure(call: Call<PostResSignIn>, t: Throwable) {
                 Log.d(ContentValues.TAG, "POST 실패 : $t")
             }

         })

    }




}
