package com.example.cpt_odos_diary

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cpt_odos_diary.App.App
import com.example.cpt_odos_diary.App.TokenSharedPreferences
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


       btnEvent.setOnClickListener{
        val email : String = binding.ETemail.text.toString()
        val pw : String = binding.ETpw.text.toString()
        val requestData = PostReqSignIn(email,pw)
           val signApi : LoginApi = RetrofitCreator.signApi
           // 데이터를 묶어서 반환값으로 res이미 받음. 어떻게 처리할건데?
        val callSignIn = signApi.postSignIn(requestData)
        resultPostSignIn(callSignIn);
        //App.token_prefs.accessToken?.let { it1 -> Log.d(ContentValues.TAG,it1)

    }
    signUpBtn.setOnClickListener{

        val intent = Intent(this, SignupActivity::class.java)

        startActivity(intent)
    }
}
    private fun resultPostSignIn(callSignIn: Call<PostResSignIn>) {
         callSignIn.enqueue(object: Callback<PostResSignIn>{
             override fun onResponse(
                 call: Call<PostResSignIn>,
                 response: Response<PostResSignIn>
             ) {
                 if(response.body()?.success == true) {
                     Log.d(ContentValues.TAG, "signIn Post 성공 : ${response.body()}")
                    //response.body()?.success
                     // shared Preference 캐시 // jwt, uid 안드로이드에 캐싱처리
                     App.token_prefs.accessToken = response.body()?.data?.get(0)?.token
                     Log.d(TAG, "성공 token : ${App.token_prefs.accessToken}")// sharedPreference에 데이터 저장 후 호출
                     App.token_prefs.uid = response.body()?.data?.get(0)?.uid
                     Log.d(TAG, "성공 uid : ${App.token_prefs.uid}")// sharedPreference에 데이터 저장 후 호출
                     Toast.makeText( applicationContext,"로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                     if(!App.token_prefs.uid.isNullOrBlank()) {
                         finish()
                     }

                 }
                 else {
                     Log.d(ContentValues.TAG, "signIn Post 실패 : ${response.body()}")
                     Toast.makeText( applicationContext,"로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                 }
             }
             override fun onFailure(call: Call<PostResSignIn>, t: Throwable) {
                 Log.d(ContentValues.TAG, "signIn POST 통신 에러 : $t")
             }
         })
    }
}
