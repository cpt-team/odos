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
import androidx.navigation.fragment.findNavController
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
        Log.d(TAG, "날짜는 $cYear, $cMonth ${!App.token_prefs.dayList?.get(0).isNullOrBlank()}")

        App.token_prefs.odosYear = cYear
        App.token_prefs.odosMonth = cMonth

        // diaryFragment 들어오자마자 현재 날짜에 맞는 데이터 받기.
        //데이터 호출
            retrofitGetAllDiary(
                diaryApi, uid.toString(), cYear.toString(), cMonth.toString()
            )
            // 서버에서 받은 데이터를 it 변수에 리스트로 담고 있는 상태.
            {

                //App.token_prefs.diaryCnt = it.size.toString()
                val monthDate = mutableListOf<String>()

                for (i in it.indices) {
                    val date1 = it[i].createAt.split(" ")[0]
                    monthDate.add(date1.split("-")[2])
                }

                App.token_prefs.dayList = monthDate

                //Log.d(TAG, "cntDiary: ${App.token_prefs.diaryCnt}")

                Log.d(TAG, "monthDate: $monthDate")
                val dateCheck = view?.findViewById<TextView>(R.id.monthDate)
                if (dateCheck != null) {
                    dateCheck.text = monthDate.toString()
                }
            }

        Log.d(TAG,"데이터는 ${App.token_prefs.dayList}")


            calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
                Log.d(TAG,"데이터 month 불러와집니다${dayOfMonth}")
                Log.d(TAG,"데이터 밖 불러와집니다${App.token_prefs.dayList!!.contains(dayOfMonth.toString())}")

                // true 잘 뜸
                if(App.token_prefs.dayList!!.contains(dayOfMonth.toString())){
                    Log.d(TAG,"데이터 잘 불러와집니다${App.token_prefs.dayList!!.contains(dayOfMonth.toString())}")


                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)



                    val intent = Intent(activity, DiaryGetActivity::class.java)


                    // 날짜 정보를 Intent에 추가
                    intent.putExtra("selectedDate", selectedDate.timeInMillis)
                    startActivity(intent)


                } else if(!App.token_prefs.dayList!!.contains(dayOfMonth.toString())){

                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    dateFormat.format(selectedDate.timeInMillis)

                    val formattedDate = dateFormat.format(selectedDate.timeInMillis)

                    Log.d(TAG,"비교: ${formattedDate},${onlyDate}, ${formattedDate > onlyDate.toString()}")
                    if(formattedDate > onlyDate.toString()){
                        Toast.makeText(context, "현재 날짜보다 앞선 날짜입니다!", Toast.LENGTH_SHORT).show()
                        return@setOnDateChangeListener
                    }
                    val intent = Intent(activity, DiaryEditActivity::class.java)


                    // 날짜 정보를 Intent에 추가
                    intent.putExtra("selectedDate", selectedDate.timeInMillis)
                    startActivity(intent)

                }
                val dateCheck = view?.findViewById<TextView>(R.id.monthDate)
                if (dateCheck != null) {
                    dateCheck.text = App.token_prefs.dayList.toString()
                }

                calendarView.setOnClickListener(null)
            }





            if (!App.token_prefs.dayList?.get(0).isNullOrBlank()) {
                // 현재 시간 출력 year,month -> String으로 서버에 넘겨야 하기 때문에 toString()
                Log.d(TAG, "날짜는??? $cYear, $cMonth")
                Log.d(TAG, "데이터는!!! ${App.token_prefs.dayList}")


                // 날짜가 눌렸을 때 반응..
                calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->


                    Log.d(TAG, "date: $onlyDate")
                    // 클릭한 날짜 정보를 가져오기
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)

                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    dateFormat.format(selectedDate.timeInMillis)


                    val formattedDate = dateFormat.format(selectedDate.timeInMillis)

                    Log.d(TAG,"비교: ${formattedDate},${onlyDate}, ${formattedDate > onlyDate.toString()}")
                    if(formattedDate > onlyDate.toString()){
                        Toast.makeText(context, "현재 날짜보다 앞선 날짜입니다!", Toast.LENGTH_SHORT).show()
                        return@setOnDateChangeListener
                    }


                    Log.d(TAG, "data: first diary작성")
                    // DiaryEditActivity로 이동하는 Intent를 생성
                    val intent = Intent(activity, DiaryEditActivity::class.java)
                    //intent.flags = FLAG_ACTIVITY_CLEAR_TOP
                    // 날짜 정보를 Intent에 추가
                    intent.putExtra("selectedDate", selectedDate.timeInMillis)
                    startActivity(intent)

                    calendarView.setOnClickListener(null)
                }
            }



        return view
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()

        //App.token_prefs.dayList = listOf()
        Log.d(TAG,"diaryCnt: ${App.token_prefs.diaryCnt}")

        Log.d(TAG,"odosCnt: ${App.token_prefs.odosnt}")

        Log.d(TAG,"dayList: ${App.token_prefs.dayList}")

        val calendarView = view?.findViewById<CalendarView>(R.id.calendarview)
        val previousBtn = view?.findViewById<TextView>(R.id.previousMonthBtn)
        val afterBtn = view?.findViewById<TextView>(R.id.afterMonthBtn)

        val diaryApi: DiaryApi = RetrofitCreator.diaryApi

        // req /diary에 사용할 데이터 변수 선언
        val uid: String? = App.token_prefs.uid


        Log.d(TAG, "uid는 $uid")




        // 현재 시간 출력 year,month -> String으로 서버에 넘겨야 하기 때문에 toString()
        val onlyDate: LocalDate = LocalDate.now()
        App.token_prefs.odosYear = onlyDate.year
        App.token_prefs.odosMonth = onlyDate.monthValue


        Log.d(TAG,"리저메 날짜: ${App.token_prefs.odosYear}, ${App.token_prefs.odosMonth}")
        Log.d(TAG, "날짜는 ${App.token_prefs.odosYear}, ${App.token_prefs.odosMonth}")

        // 달력 왼쪽 버튼 클릭되면 !!
        previousBtn?.setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {

                    Log.d(TAG,"데이터는 ${App.token_prefs.dayList}")

                    if (App.token_prefs.odosMonth == 1) {
                        App.token_prefs.odosMonth = 12
                        App.token_prefs.odosYear = App.token_prefs.odosYear?.minus(1)
                        Log.d(TAG, "cMonth: ${App.token_prefs.odosMonth}, cYear: ${App.token_prefs.odosYear}")
                    } else {
                        App.token_prefs.odosMonth = App.token_prefs.odosMonth?.minus(1)
                        Log.d(TAG, "cMonth: ${App.token_prefs.odosMonth}, cYear: ${App.token_prefs.odosYear}")
                    }
                    val dateCheck = view?.findViewById<TextView>(R.id.monthDate)
                    if (dateCheck != null) {
                        dateCheck.text = "비어있습니다"
                    }




                    //데이터 호출
                    if (uid != null) {
                        retrofitGetAllDiary(
                            diaryApi,
                            uid.toString(),
                            App.token_prefs.odosYear.toString(),
                            App.token_prefs.odosMonth.toString()
                        ) {


                            val monthDate = mutableListOf<String>()

                            for (i in it.indices) {
                                val date1 = it[i].createAt.split(" ")[0]
                                monthDate.add(date1.split("-")[2])
                            }


                            App.token_prefs.dayList = monthDate
                            if (dateCheck != null) {
                                dateCheck.text = App.token_prefs.dayList.toString()

                                Log.d(TAG, "monthDate: $monthDate")


                            }

                            Log.d(TAG,"monthDate: ${monthDate}")



                            val selectedDate = Calendar.getInstance()
                            calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
                                if (App.token_prefs.dayList!!.contains(dayOfMonth.toString())) {

                                    selectedDate.set(year, month, dayOfMonth)


                                    val intent = Intent(activity, DiaryGetActivity::class.java)
                                    // 날짜 정보를 Intent에 추가
                                    intent.putExtra("selectedDate", selectedDate.timeInMillis)
                                    startActivity(intent)


                                } else {

                                    selectedDate.set(year, month, dayOfMonth)
                                    val dateFormat =
                                        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                    val formattedDate = dateFormat.format(selectedDate.timeInMillis)
                                    if (formattedDate > onlyDate.toString()) {
                                        Toast.makeText(
                                            context,
                                            "현재 날짜보다 앞선 날짜입니다!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return@setOnDateChangeListener
                                    }


                                    val intent = Intent(activity, DiaryEditActivity::class.java)

                                    // 날짜 정보를 Intent에 추가
                                    intent.putExtra("selectedDate", selectedDate.timeInMillis)
                                    startActivity(intent)


                                }

                            }

                        }
                    }else {
                        Log.d(TAG, "uid가 존재하지 않습니다")
                    }

                }
            }
            false
        }
        // 달력 오른쪽 버튼 눌렀을 때!!
        afterBtn?.setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {


                    Log.d(TAG,"데이터는 ${App.token_prefs.dayList}")

                    if (App.token_prefs.odosMonth == 12) {
                        App.token_prefs.odosMonth = 1
                        App.token_prefs.odosYear = App.token_prefs.odosYear?.plus(1)
                        Log.d(TAG, "cMonth: ${App.token_prefs.odosMonth}, cYear: ${App.token_prefs.odosYear}")
                    } else {
                        App.token_prefs.odosMonth = App.token_prefs.odosMonth?.plus(1)
                        Log.d(TAG, "cMonth: ${App.token_prefs.odosMonth}")
                    }

                    val dateCheck = view?.findViewById<TextView>(R.id.monthDate)
                    if (dateCheck != null) {
                        dateCheck.text = "비어있습니다"
                    }



                    //데이터 호출
                    if (uid != null) {
                        retrofitGetAllDiary(diaryApi, uid, App.token_prefs.odosYear.toString(), App.token_prefs.odosMonth.toString()){
                           // App.token_prefs.diaryCnt = it.size.toString()
                            val monthDate = mutableListOf<String>()

                            for (i in it.indices) {
                                val date1 = it[i].createAt.split(" ")[0]
                                monthDate.add(date1.split("-")[2])
                            }
                                App.token_prefs.dayList = monthDate
                                Log.d(TAG,"monthDate: ${monthDate}")
                                Log.d(TAG,"monthDate: ${App.token_prefs.dayList}")

                                if (dateCheck != null) {
                                    dateCheck.text = App.token_prefs.dayList.toString()

                                }






                            //Log.d(TAG, "cntDiary: ${App.token_prefs.diaryCnt}")

                            Log.d(TAG, "monthDate: $monthDate")


                        }

                        val selectedDate = Calendar.getInstance()
                        calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
                            if(App.token_prefs.dayList!!.contains(dayOfMonth.toString())){


                                val intent = Intent(activity, DiaryGetActivity::class.java)

                                selectedDate.set(year, month, dayOfMonth)
                                // 날짜 정보를 Intent에 추가
                                intent.putExtra("selectedDate", selectedDate.timeInMillis)
                                startActivity(intent)


                            } else{

                                selectedDate.set(year, month, dayOfMonth)
                                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                val formattedDate = dateFormat.format(selectedDate.timeInMillis)
                                if(formattedDate > onlyDate.toString()){
                                    Toast.makeText(context, "현재 날짜보다 앞선 날짜입니다!", Toast.LENGTH_SHORT).show()
                                    return@setOnDateChangeListener
                                }

                                val intent = Intent(activity, DiaryEditActivity::class.java)

                                // 날짜 정보를 Intent에 추가
                                intent.putExtra("selectedDate", selectedDate.timeInMillis)
                                startActivity(intent)

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
