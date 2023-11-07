package com.example.cpt_odos_diary

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.cpt_odos_diary.App.App
import com.example.cpt_odos_diary.databinding.ActivitySigninBinding
import com.example.cpt_odos_diary.databinding.FragmentDiaryBinding
import com.example.cpt_odos_diary.retrofit.DiaryApi
import com.example.cpt_odos_diary.retrofit.GetReqCallAllDiary
import com.example.cpt_odos_diary.retrofit.GetResCallAllDiary
import com.example.cpt_odos_diary.retrofit.RetrofitCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.util.Objects


class DiaryFragment : Fragment() {

    private lateinit var binding : FragmentDiaryBinding


    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?




    ): View? {
        val view = inflater.inflate(R.layout.fragment_diary, container, false)


        val calendarView = view.findViewById<CalendarView>(R.id.calendarview)
        val previousBtn = view.findViewById<TextView>(R.id.previousMonthBtn)
        val afterBtn = view.findViewById<TextView>(R.id.afterMonthBtn)

        //retrofit
        val diaryApi: DiaryApi = RetrofitCreator.diaryApi

        // req /diary에 사용할 데이터 변수 선언
        val uid : String? = App.token_prefs.uid
        Log.d(TAG,"uid: $uid")



        // 현재 시간 출력 year,month -> String으로 서버에 넘겨야 하기 때문에 toString()
        val onlyDate: LocalDate = LocalDate.now()
        var cYear = onlyDate.year // 2023
        var cMonth = onlyDate.monthValue // 11
        Log.d(TAG,"uid: $cYear, $cMonth")

        // diaryFragment 들어오자마자 현재 날짜에 맞는 데이터 받기.
        //데이터 호출
        if (uid != null) {
            retrofitGetAllDiary(diaryApi,uid.toString(),cYear.toString(),cMonth.toString())
        }
        else{
            Log.d(TAG,"uid가 존재하지 않습니다")
        }



        // 달력 왼쪽 버튼 클릭되면 !!
        previousBtn.setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (cMonth == 1) {
                        cMonth = 12
                        cYear -= 1
                        Log.d(TAG, "cMonth: $cMonth, cYear: $cYear")
                    } else {
                        cMonth -= 1
                        Log.d(TAG, "cMonth: $cMonth")
                    }
                    //데이터 호출
                    if (uid != null) {
                        retrofitGetAllDiary(diaryApi,uid.toString(),cYear.toString(),cMonth.toString())
                    }
                    else{
                        Log.d(TAG,"uid가 존재하지 않습니다")
                    }

                }
            }
            false
        }

        // 달력 오른쪽 버튼 눌렀을 때!!
        afterBtn.setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (cMonth == 12) {
                        cMonth = 1
                        cYear += 1
                        Log.d(TAG, "cMonth: $cMonth, cYear: $cYear")
                    } else {
                        cMonth += 1
                        Log.d(TAG, "cMonth: $cMonth")
                    }
                    //데이터 호출
                    if (uid != null) {
                        retrofitGetAllDiary(diaryApi,uid,cYear.toString(),cMonth.toString())
                    }
                    else{
                        Log.d(TAG,"uid가 존재하지 않습니다")
                    }
                }
            }
            false
        }

        // 날짜가 눌렸을 때 반응..
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // 달력 > 버튼 눌렀을 때 year,month값 받아오기
            // -> year,month 값을 계속 관찰.. 현재값과 다를경우 새로운 변수 넣기


            // 클릭한 날짜 정보를 가져오기
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)

            // DiaryEditActivity로 이동하는 Intent를 생성
            val intent = Intent(activity, DiaryEditActivity::class.java)

            // 날짜 정보를 Intent에 추가
            intent.putExtra("selectedDate", selectedDate.timeInMillis)

            // DiaryEditActivity 시작
            startActivity(intent)
        }

        return view
    }
}

fun retrofitGetAllDiary(diaryApi: DiaryApi, uid : String, year: String, month: String){

    val callAllDiary = diaryApi.getCallAllDiary(uid,year,month)
    resultGetAllDiary(callAllDiary)
}

fun resultGetAllDiary(callAllDiary : Call<GetResCallAllDiary>) {
    callAllDiary.enqueue(object : Callback<GetResCallAllDiary>{
        override fun onResponse(
            call: Call<GetResCallAllDiary>,
            response: Response<GetResCallAllDiary>
        ) {
            if(response.isSuccessful) {
                Log.d(ContentValues.TAG, "/diary get 성공 : ${response.body()}")
                Log.d(ContentValues.TAG, "성공 message : ${response.body()?.message}")
                Log.d(ContentValues.TAG, "성공 status : ${response.body()?.status}")
                Log.d(ContentValues.TAG, "성공 success : ${response.body()?.success}")
                Log.d(ContentValues.TAG, "성공 data : ${response.body()?.data}")
            }
            else {
                Log.d(ContentValues.TAG, "/diary get 실패 : ${response.body()}")
                Log.d(ContentValues.TAG, "실패 message : ${response.body()?.message}")
                Log.d(ContentValues.TAG, "실패 status : ${response.body()?.status}")
                Log.d(ContentValues.TAG, "실패 success : ${response.body()?.success}")
            }
        }

        override fun onFailure(call: Call<GetResCallAllDiary>, t: Throwable) {
            Log.d(ContentValues.TAG, "/diary get 실패 : $t")
        }

    })
}