package com.example.cpt_odos_diary

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.cpt_odos_diary.App.App
import com.example.cpt_odos_diary.retrofit.DataList
import com.example.cpt_odos_diary.retrofit.DiaryApi
import com.example.cpt_odos_diary.retrofit.GetResCallAllDiary
import com.example.cpt_odos_diary.retrofit.RetrofitCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.util.Locale


class DiaryFragment : Fragment() {
    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_diary, container, false)



        val calendarView = view?.findViewById<CalendarView>(R.id.calendarview)


        // 페이지 열릴 때 확인용
        Log.d(TAG, "/diary페이지 시작")
        //retrofit
        val diaryApi: DiaryApi = RetrofitCreator.diaryApi

        // req /diary에 사용할 데이터 변수 선언
        val uid: String? = App.token_prefs.uid

        Log.d(TAG, "uid는 $uid")


        // 현재 시간 출력 year,month -> String으로 서버에 넘겨야 하기 때문에 toString()
        val onlyDate: LocalDate = LocalDate.now()
        var cYear = onlyDate.year // 2023
        var cMonth = onlyDate.monthValue // 11
        Log.d(TAG, "날짜는 $cYear, $cMonth")

        // diaryFragment 들어오자마자 현재 날짜에 맞는 데이터 받기.
        //데이터 호출
        if (uid != null) {
            retrofitGetAllDiary(
                diaryApi, uid.toString(), cYear.toString(), cMonth.toString()
            )
            // 서버에서 받은 데이터를 it 변수에 리스트로 담고 있는 상태.
            {

                App.token_prefs.diaryCnt = it.size.toString()
                val monthDate = mutableListOf<String>()

                for (i in it.indices) {
                    val date1 = it[i].createAt.split(" ")[0]
                    monthDate.add(date1.split("-")[2])
                }

                Log.d(TAG, "monthDate: $monthDate")
                val dateCheck = view?.findViewById<TextView>(R.id.monthDate)
                if (dateCheck != null) {
                    dateCheck.text = monthDate.toString()
                }

                Log.d(TAG, "cntDiary: ${App.token_prefs.diaryCnt}")


                // 날짜가 눌렸을 때 반응..
                if (calendarView != null) {
                    calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
                        // 달력 > 버튼 눌렀을 때 year,month값 받아오기
                        // -> year,month 값을 계속 관찰.. 현재값과 다를경우 새로운 변수 넣기

                        Log.d(TAG, "date: $onlyDate")

                        // 클릭한 날짜 정보를 가져오기
                        val selectedDate = Calendar.getInstance()
                        selectedDate.set(year, month, dayOfMonth)

                        //Log.d(TAG,"date: ${selectedDate.timeInMillis}")
                        Log.d(TAG, "date data: $it")

                        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val formattedDate = dateFormat.format(selectedDate.timeInMillis)
                        // Log.d(TAG, "date: $formattedDate") // 받은 날짜 보여줌. 날짜 잘 나옴 ㅎㅎ
                        Log.d(TAG, "date: $formattedDate")
                        Log.d(TAG, "date: $onlyDate")
                        if (formattedDate > onlyDate.toString()) {
                            Toast.makeText(context, "현재 날짜보다 앞선 날짜입니다!", Toast.LENGTH_SHORT).show()
                            return@setOnDateChangeListener
                        }


                        // data[]을 반복문으로 돌려서 클릭한 날짜의 데이터가 존재하는 지 확인
                        for (i in it.indices) {


                            Log.d(TAG, "data: " + it[i].createAt.split(" ")[0])

                            // data[]에 날짜 데이터가 존재할 경우.
                            if (it[i].createAt.split(" ")[0] == formattedDate) {
                                // intent로 존재하는 데이터 뿌려주기
                                Log.d(TAG, "data 존재함")
                                val intent = Intent(activity, DiaryGetActivity::class.java)

                                // 날짜 정보를 Intent에 추가
                                intent.putExtra("selectedDate", selectedDate.timeInMillis)

                                // 데이터 존재할 시 데이터 가져옴.
                                intent.putExtra("diaryId", it[i].did)
                                intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)


                            }

                            // diary 데이터가 존재하지 않을 때 create 페이지 호출
                            if (it[i].createAt.split(" ")[0] != formattedDate) {

                                Log.d(TAG, "data 나도 호출되냐?$formattedDate")
                                // DiaryEditActivity로 이동하는 Intent를 생성
                                val intent = Intent(activity, DiaryEditActivity::class.java)

                                //intent.flags = FLAG_ACTIVITY_CLEAR_TOP
                                // 날짜 정보를 Intent에 추가
                                intent.putExtra("selectedDate", selectedDate.timeInMillis)
                                startActivity(intent)


                            }


                        }

                        calendarView.setOnClickListener(null)
                    }
                }
            }
        } else {
            Log.d(TAG, "uid가 존재하지 않습니다")
        }

        return view
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()

        val calendarView = view?.findViewById<CalendarView>(R.id.calendarview)
        val previousBtn = view?.findViewById<TextView>(R.id.previousMonthBtn)
        val afterBtn = view?.findViewById<TextView>(R.id.afterMonthBtn)

        val diaryApi: DiaryApi = RetrofitCreator.diaryApi

        // req /diary에 사용할 데이터 변수 선언
        val uid: String? = App.token_prefs.uid

        Log.d(TAG, "uid는 $uid")


        // 현재 시간 출력 year,month -> String으로 서버에 넘겨야 하기 때문에 toString()
        val onlyDate: LocalDate = LocalDate.now()
        var cYear = onlyDate.year // 2023
        var cMonth = onlyDate.monthValue // 11
        Log.d(TAG, "날짜는 $cYear, $cMonth")

        // 달력 왼쪽 버튼 클릭되면 !!
        if (previousBtn != null) {
            previousBtn.setOnTouchListener { _, event ->
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        var dateCheck = view?.findViewById<TextView>(R.id.monthDate)
                        dateCheck?.text = " "
                        if (cMonth == 1) {
                            cMonth = 12
                            cYear -= 1
                            Log.d(TAG, "cMonth: $cMonth, cYear: $cYear")
                        } else {
                            cMonth -= 1
                            Log.d(TAG, "cMonth: $cMonth, cYear: $cYear")
                        }


                        //데이터 호출
                        if (uid != null) {
                            retrofitGetAllDiary(
                                diaryApi,
                                uid.toString(),
                                cYear.toString(),
                                cMonth.toString()){
                                    val monthDate = mutableListOf<String>()
                                    for (i in it.indices) {
                                        val date1 = it[i].createAt.split(" ")[0]
                                        monthDate.add(date1.split("-")[2])
                                    }
                                    Log.d(TAG,"monthDate: $monthDate")
                                    dateCheck = view?.findViewById<TextView>(R.id.monthDate)


                                // 날짜가 눌렸을 때 반응..
                                if (calendarView != null) {
                                    calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
                                        // 달력 > 버튼 눌렀을 때 year,month값 받아오기
                                        // -> year,month 값을 계속 관찰.. 현재값과 다를경우 새로운 변수 넣기

                                        Log.d(TAG, "date: $onlyDate")

                                        // 클릭한 날짜 정보를 가져오기
                                        val selectedDate = Calendar.getInstance()
                                        selectedDate.set(year, month, dayOfMonth)

                                        //Log.d(TAG,"date: ${selectedDate.timeInMillis}")
                                        Log.d(TAG, "date data: $it")

                                        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                        val formattedDate = dateFormat.format(selectedDate.timeInMillis)
                                        // Log.d(TAG, "date: $formattedDate") // 받은 날짜 보여줌. 날짜 잘 나옴 ㅎㅎ
                                        Log.d(TAG, "date: $formattedDate")
                                        Log.d(TAG, "date: $onlyDate")
                                        if (formattedDate > onlyDate.toString()) {
                                            Toast.makeText(context, "현재 날짜보다 앞선 날짜입니다!", Toast.LENGTH_SHORT).show()
                                            return@setOnDateChangeListener
                                        }


                                        // data[]을 반복문으로 돌려서 클릭한 날짜의 데이터가 존재하는 지 확인
                                        for (i in it.indices) {


                                            Log.d(TAG, "data: " + it[i].createAt.split(" ")[0])

                                            // data[]에 날짜 데이터가 존재할 경우.
                                            if (it[i].createAt.split(" ")[0] == formattedDate) {
                                                // intent로 존재하는 데이터 뿌려주기
                                                Log.d(TAG, "data 존재함")
                                                val intent = Intent(activity, DiaryGetActivity::class.java)

                                                // 날짜 정보를 Intent에 추가
                                                intent.putExtra("selectedDate", selectedDate.timeInMillis)

                                                // 데이터 존재할 시 데이터 가져옴.
                                                intent.putExtra("diaryId", it[i].did)
                                                intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK)
                                                startActivity(intent)


                                            }

                                            // diary 데이터가 존재하지 않을 때 create 페이지 호출
                                            if (it[i].createAt.split(" ")[0] != formattedDate) {

                                                Log.d(TAG, "data 나도 호출되냐?$formattedDate")
                                                // DiaryEditActivity로 이동하는 Intent를 생성
                                                val intent = Intent(activity, DiaryEditActivity::class.java)

                                                //intent.flags = FLAG_ACTIVITY_CLEAR_TOP
                                                // 날짜 정보를 Intent에 추가
                                                intent.putExtra("selectedDate", selectedDate.timeInMillis)
                                                startActivity(intent)


                                            }


                                        }

                                    }
                                }

                                if (dateCheck != null) {
                                    dateCheck!!.text = monthDate.toString()
                                }


                                }
                        } else {
                            Log.d(TAG, "uid가 존재하지 않습니다")
                        }

                    }
                }
                false
            }
        }
        // 달력 오른쪽 버튼 눌렀을 때!!
        if (afterBtn != null) {
            var dateCheck = view?.findViewById<TextView>(R.id.monthDate)
            dateCheck?.text = " "
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
                            retrofitGetAllDiary(diaryApi, uid, cYear.toString(), cMonth.toString()){
                                val monthDate = mutableListOf<String>()
                                for (i in it.indices) {
                                    val date1 = it[i].createAt.split(" ")[0]
                                    Log.d(TAG,"month:${it[i].createAt.split(" ")[0]}")
                                    monthDate.add(date1.split("-")[2])
                                }
                                Log.d(TAG,"monthDate: $monthDate")
                                dateCheck = view?.findViewById<TextView>(R.id.monthDate)
                                if (dateCheck != null) {
                                    dateCheck!!.text = monthDate.toString()
                                }


                                // 날짜가 눌렸을 때 반응..
                                if (calendarView != null) {
                                    calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
                                        // 달력 > 버튼 눌렀을 때 year,month값 받아오기
                                        // -> year,month 값을 계속 관찰.. 현재값과 다를경우 새로운 변수 넣기

                                        Log.d(TAG, "date: $onlyDate")

                                        // 클릭한 날짜 정보를 가져오기
                                        val selectedDate = Calendar.getInstance()
                                        selectedDate.set(year, month, dayOfMonth)

                                        //Log.d(TAG,"date: ${selectedDate.timeInMillis}")
                                        Log.d(TAG, "date data: $it")

                                        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                        val formattedDate = dateFormat.format(selectedDate.timeInMillis)
                                        // Log.d(TAG, "date: $formattedDate") // 받은 날짜 보여줌. 날짜 잘 나옴 ㅎㅎ
                                        Log.d(TAG, "date: $formattedDate")
                                        Log.d(TAG, "date: $onlyDate")
                                        if (formattedDate > onlyDate.toString()) {
                                            Toast.makeText(context, "현재 날짜보다 앞선 날짜입니다!", Toast.LENGTH_SHORT).show()
                                            return@setOnDateChangeListener
                                        }


                                        // data[]을 반복문으로 돌려서 클릭한 날짜의 데이터가 존재하는 지 확인
                                        for (i in it.indices) {


                                            Log.d(TAG, "data: " + it[i].createAt.split(" ")[0])

                                            // data[]에 날짜 데이터가 존재할 경우.
                                            if (it[i].createAt.split(" ")[0] == formattedDate) {
                                                // intent로 존재하는 데이터 뿌려주기
                                                Log.d(TAG, "data 존재함")
                                                val intent = Intent(activity, DiaryGetActivity::class.java)

                                                // 날짜 정보를 Intent에 추가
                                                intent.putExtra("selectedDate", selectedDate.timeInMillis)

                                                // 데이터 존재할 시 데이터 가져옴.
                                                intent.putExtra("diaryId", it[i].did)
                                                intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK)
                                                startActivity(intent)


                                            }

                                            // diary 데이터가 존재하지 않을 때 create 페이지 호출
                                            if (it[i].createAt.split(" ")[0] != formattedDate) {

                                                Log.d(TAG, "data 나도 호출되냐?$formattedDate")
                                                // DiaryEditActivity로 이동하는 Intent를 생성
                                                val intent = Intent(activity, DiaryEditActivity::class.java)

                                                //intent.flags = FLAG_ACTIVITY_CLEAR_TOP
                                                // 날짜 정보를 Intent에 추가
                                                intent.putExtra("selectedDate", selectedDate.timeInMillis)
                                                startActivity(intent)


                                            }


                                        }

                                    }
                                }

                                if (dateCheck != null) {
                                    dateCheck!!.text = monthDate.toString()
                                }


                            }
                        } else {
                            Log.d(TAG, "uid가 존재하지 않습니다")
                        }
                    }
                }
                false
            }
        }
    }

}
fun retrofitGetAllDiary(diaryApi: DiaryApi, uid : String, year: String, month: String, onSuccess: (List<DataList>) -> Unit = {}){
    val callAllDiary = diaryApi.getCallAllDiary(uid,year,month)
    resultGetAllDiary(callAllDiary,onSuccess)
}
fun resultGetAllDiary(callAllDiary : Call<GetResCallAllDiary>, onSuccess: (List<DataList>) -> Unit = {}){
    callAllDiary.enqueue(object : Callback<GetResCallAllDiary>{
        override fun onResponse(
            call: Call<GetResCallAllDiary>,
            response: Response<GetResCallAllDiary>
        ) {
            if(response.body()?.success == true) {
                Log.d(ContentValues.TAG, "/diary get 성공 : ${response.body()}")

                onSuccess(response.body()?.data!!)
            }
            else {
                Log.d(ContentValues.TAG, "/diary get 실패 : ${response.body()}")

            }
        }
        override fun onFailure(call: Call<GetResCallAllDiary>, t: Throwable) {
            Log.d(ContentValues.TAG, "/diary get 통신 에러: $t")
        }
    })
}

// 서버에서 받아온 데이터를 기반으로 선택한 날짜를 계산하는 함수
