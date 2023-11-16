package com.example.cpt_odos_diary

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.example.cpt_odos_diary.App.App
import com.example.cpt_odos_diary.databinding.ActivityDiaryEditBinding
import com.example.cpt_odos_diary.retrofit.DataList
import com.example.cpt_odos_diary.retrofit.DiaryApi
import com.example.cpt_odos_diary.retrofit.DiaryId
import com.example.cpt_odos_diary.retrofit.GetResCallAllDiary
import com.example.cpt_odos_diary.retrofit.PostReqCreateDiary
import com.example.cpt_odos_diary.retrofit.PostResCreateDiary
import com.example.cpt_odos_diary.retrofit.RetrofitCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class DiaryEditActivity : AppCompatActivity() {
    lateinit var binding: ActivityDiaryEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityDiaryEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)


        val dateTextView = binding.dateTextView
        val checkIV = binding.ivCheck


        checkIV.setOnClickListener {


            //retrofit
            val diaryApi: DiaryApi = RetrofitCreator.diaryApi
            // req /diary에 사용할 데이터 변수 선언
            val uid: String? = App.token_prefs.uid
            val title: String = binding.Etitle.text.toString()
            val content: String = binding.Econtent.text.toString()

            val emotions = binding.rgMood
            val whethers = binding.rgWeather

            val whether: String = when (whethers.checkedRadioButtonId) {
                R.id.RB_sunny -> {
                    "sunny"
                }

                R.id.RB_sunnyCloudy -> {
                    "sunnyCloudy"
                }

                R.id.RB_windy -> {
                    "windy"
                }

                R.id.RB_Cloudy -> {
                    "cloudy"
                }

                R.id.RB_rainy -> {
                    "rainy"
                }

                R.id.RB_snowy -> {
                    "snowy"
                }

                else -> {
                    "null"
                }
            }
            val emotion: String = when (emotions.checkedRadioButtonId) {
                R.id.RB_happy -> "happy"
                R.id.RB_love -> "love"
                R.id.RB_sleepy -> "sleepy"
                R.id.RB_sad -> "sad"
                R.id.RB_fine -> "fine"
                R.id.RB_angry -> "angry"

                else -> {
                    "null"
                }
            }
            Log.d(TAG, "data: $uid, ${binding.Etitle.text}, $content, $emotion, $whether")

            // 데이터 서버로 전송하고 다시 다이어리 탭으로 돌아가기
            if (uid != null) {
                Log.d(TAG, "data: $uid, $title, $content, $emotion, $whether")
                retrofitPostCreateDiary(diaryApi, uid, title, content, emotion, whether,
                    onSuccess = {
                        Log.d(TAG, "/diary DID: $it")
                    })
            }


        }

        val backBtn = binding.ivBack
        backBtn.setOnClickListener {
            finish()
        }

        // Intent에서 선택한 날짜 정보를 추출
        val selectedDateMillis = intent.getLongExtra("selectedDate", 0)

        if (selectedDateMillis > 0) {
            val selectedDate = Calendar.getInstance()
            selectedDate.timeInMillis = selectedDateMillis

            // 선택한 날짜를 TextView에 표시
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)
            dateTextView.text = formattedDate

            Log.d(TAG, "date: " + formattedDate) // 받은 날짜 보여줌.
        }


        val backbtn = findViewById<ImageView>(R.id.iv_back)
        backbtn.setOnClickListener {
            finish()
        }

    }
}

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
                    Log.d(TAG,"/diary data: ${response.body()?.data}")

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





