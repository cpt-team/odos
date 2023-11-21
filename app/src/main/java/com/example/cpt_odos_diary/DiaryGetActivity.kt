package com.example.cpt_odos_diary

import android.content.ContentValues
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cpt_odos_diary.App.App
import com.example.cpt_odos_diary.databinding.ActivityDiaryGetBinding
import com.example.cpt_odos_diary.retrofit.DiaryApi
import com.example.cpt_odos_diary.retrofit.DiaryList
import com.example.cpt_odos_diary.retrofit.GetResCallDiary
import com.example.cpt_odos_diary.retrofit.PutReqUpdateDiary
import com.example.cpt_odos_diary.retrofit.PutResUpdateDiary
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
        val uid = App.token_prefs.uid

        // Intent에서 선택한 날짜 정보를 추출
        val selectedDateMillis = intent.getLongExtra("selectedDate", 0)
            val selectedDate = Calendar.getInstance()
            selectedDate.timeInMillis = selectedDateMillis
            // 선택한 날짜를 TextView에 표시
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)
            dateTextView.text = formattedDate
            Log.d(ContentValues.TAG, "date: " + formattedDate) // 받은 날짜 보여줌.


        //retrofit
        val diaryApi: DiaryApi = RetrofitCreator.diaryApi

        if (uid != null) {
            retrofitGetDiary(diaryApi, uid, formattedDate,
                onSuccess = {
                    binding.Etitle.setText(it[0].title)
                    binding.Econtent.setText(it[0].content)
                    when (it[0].emotion) {
                        "sad" -> binding.RBSad.isChecked = true
                        "happy" -> binding.RBHappy.isChecked = true
                        "fine" -> binding.RBFine.isChecked = true
                        "love" -> binding.RBLove.isChecked = true
                        "angry" -> binding.RBAngry.isChecked = true
                        "sleepy" -> binding.RBSleepy.isChecked = true
                    }
                    when (it[0].whether) {
                        "sunny" -> binding.RBSunny.isChecked = true
                        "windy" -> binding.RBWindy.isChecked = true
                        "cloudy" -> binding.RBCloudy.isChecked = true
                        "rainy" -> binding.RBRainy.isChecked = true
                        "snowy" -> binding.RBSnowy.isChecked = true
                        "sunnyCloudy" -> binding.RBSunnyCloudy.isChecked = true
                    }

                })
        }



        val backBtn = binding.ivBack
        backBtn.setOnClickListener {
            finish()

        }

        checkIV.setOnClickListener {
            val title: String = binding.Etitle.text.toString()
            val content: String = binding.Econtent.text.toString()

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

                else -> {
                    "null"
                }
            }

            if (uid != null) {
                retrofitPutDiary(diaryApi,uid,formattedDate,title, content, emotion, whether)
            }

            finish()
            Toast.makeText(applicationContext, "diary 업데이트 되었습니다!!", Toast.LENGTH_SHORT).show()
        }

    }


}




fun retrofitGetDiary(diaryApi: DiaryApi,uid:String, createAt : String,
                            onSuccess: (List<DiaryList>) -> Unit = {}){


    val callDiary = diaryApi.getCallDiary(uid,createAt)
    resultGetDiary(callDiary,onSuccess)
}

fun resultGetDiary(callDiary : Call<GetResCallDiary>, onSuccess: (List<DiaryList>) -> Unit = {}){
    callDiary.enqueue(object : Callback<GetResCallDiary> {
        override fun onResponse(call: Call<GetResCallDiary>, response: Response<GetResCallDiary>) {
            if(response.body()?.success == true) {
                Log.d(ContentValues.TAG, "/diary/diarys get callDiary 성공 : ${response.body()}")
                Log.d(ContentValues.TAG,"/diary data: ${response.body()?.data}")

                onSuccess(response.body()?.data!!)

            }
            else {
                Log.d(ContentValues.TAG, "/diary/diarys get callDiary 실패 : ${response.body()}")

            }
        }

        override fun onFailure(call: Call<GetResCallDiary>, t: Throwable) {
            Log.d(ContentValues.TAG, "/diary/diarys get callDiary 통신 에러 : $t")
        }

    })


}

fun retrofitPutDiary(diaryApi: DiaryApi,uid : String,createAt: String, title:String, content:String, emotion: String, whether:String){
    val requestData = PutReqUpdateDiary(uid,createAt,title,content,emotion,whether)
    val callDiary = diaryApi.putUpdateDiary(requestData)
    resultPutUpdateDiary(callDiary)
}

fun resultPutUpdateDiary(callDiary : Call<PutResUpdateDiary>){
    callDiary.enqueue(object : Callback<PutResUpdateDiary> {
        override fun onResponse(
            call: Call<PutResUpdateDiary>,
            response: Response<PutResUpdateDiary>
        ) {
            if(response.body()?.success == true) {
                Log.d(ContentValues.TAG, "/diary put Diary 성공 : ${response.body()}")

            }
            else {
                Log.d(ContentValues.TAG, "/diary put Diary 실패 : ${response.body()}")

            }
        }

        override fun onFailure(call: Call<PutResUpdateDiary>, t: Throwable) {
            Log.d(ContentValues.TAG, "/diary put Diary 통신 에러 : $t")
        }

    })


}

