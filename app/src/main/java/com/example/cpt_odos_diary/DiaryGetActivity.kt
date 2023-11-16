package com.example.cpt_odos_diary

import android.content.ContentValues
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cpt_odos_diary.App.App
import com.example.cpt_odos_diary.databinding.ActivityDiaryEditBinding
import com.example.cpt_odos_diary.databinding.ActivityDiaryGetBinding
import com.example.cpt_odos_diary.retrofit.DiaryApi
import com.example.cpt_odos_diary.retrofit.DiaryId
import com.example.cpt_odos_diary.retrofit.PostReqCreateDiary
import com.example.cpt_odos_diary.retrofit.PostResCreateDiary
import com.example.cpt_odos_diary.retrofit.RetrofitCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class DiaryGetActivity : AppCompatActivity() {
    lateinit var binding: ActivityDiaryGetBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityDiaryGetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        val dateTextView = binding.dateTextView
        val checkIV = binding.ivCheck

        // Intent에서 선택한 날짜 정보를 추출
        val selectedDateMillis = intent.getLongExtra("selectedDate", 0)
        if (selectedDateMillis > 0) {
            val selectedDate = Calendar.getInstance()
            selectedDate.timeInMillis = selectedDateMillis
            // 선택한 날짜를 TextView에 표시
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)
            dateTextView.text = formattedDate
            Log.d(ContentValues.TAG, "date: " + formattedDate) // 받은 날짜 보여줌.
        }

        val diaryId = intent.getLongExtra("diaryId",0)

        //retrofit
        val diaryApi: DiaryApi = RetrofitCreator.diaryApi


        val backBtn = binding.ivBack
        backBtn.setOnClickListener {
            finish()
        }

    }


}
/*
fun retrofitPostCreateDiary(diaryApi: DiaryApi, uid : String, title: String, content: String, emotion: String, whether:String,
                            onSuccess: (List<DiaryId>) -> Unit = {}){

    val requestData = PostReqCreateDiary(uid,title,content,emotion,whether)
    val createDiary = diaryApi.postCreateDiary(requestData)
    resultPostCreateDiary(createDiary,onSuccess)
}

fun resultPostCreateDiary(createDiary : Call<PostResCreateDiary>, onSuccess: (List<DiaryId>) -> Unit = {}){
    createDiary.enqueue(object : Callback<PostResCreateDiary> {
        override fun onResponse(
            call: Call<PostResCreateDiary>,
            response: Response<PostResCreateDiary>
        ) {
            if(response.body()?.success == true) {
                Log.d(ContentValues.TAG, "/diary post 성공 : ${response.body()}")
                Log.d(ContentValues.TAG,"/diary data: ${response.body()?.data}")

                onSuccess(response.body()?.data!!)

            }
            else {
                Log.d(ContentValues.TAG, "/diary post 실패 : ${response.body()}")

            }
        }

        override fun onFailure(call: Call<PostResCreateDiary>, t: Throwable) {
            Log.d(ContentValues.TAG, "/diary post 통신 에러 : $t")
        }

    })


}

 */