package com.example.cpt_odos_diary

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cpt_odos_diary.App.App
import com.example.cpt_odos_diary.adapter.OdosAdapter
import com.example.cpt_odos_diary.databinding.FragmentOdosBinding
import com.example.cpt_odos_diary.retrofit.DataList
import com.example.cpt_odos_diary.retrofit.DiaryApi
import com.example.cpt_odos_diary.retrofit.GetResCallAllDiary
import com.example.cpt_odos_diary.retrofit.GetResCallAllOdos
import com.example.cpt_odos_diary.retrofit.OdosApi
import com.example.cpt_odos_diary.retrofit.RetrofitCreator
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.util.Locale

class OdosFragment : Fragment() {
    lateinit var binding: FragmentOdosBinding
    private var odosList: MutableList<OdosModel> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOdosBinding.inflate(inflater, container, false)
        val view = inflater.inflate(R.layout.fragment_odos, container, false)

        binding.odosRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.odosRecyclerView.adapter = OdosAdapter(activity as Context, odosList)


       // return binding.root
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        Log.d(TAG,"data odos 호출됨")
        binding.odosRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.odosRecyclerView.adapter = OdosAdapter(activity as Context, odosList)

        //retrofit
        val odosApi: OdosApi = RetrofitCreator.odosApi



        val odosCheck = binding.odosCheck
        val odosText = binding.odosTextView
        //  날짜 dialog
        odosCheck.setOnClickListener {

            val dialog = AlertDialog.Builder(context).create()
            val edialog : LayoutInflater = LayoutInflater.from(context)
            val mView : View = edialog.inflate(R.layout.dialog_datepicker,null)

            val year : NumberPicker = mView.findViewById(R.id.yearpicker_datepicker)
            val month : NumberPicker = mView.findViewById(R.id.monthpicker_datepicker)
            val cancel : Button = mView.findViewById(R.id.cancel_button_datepicker)
            val save : Button = mView.findViewById(R.id.save_button_datepicker)

            //  순환 안되게 막기
            year.wrapSelectorWheel = false
            month.wrapSelectorWheel = false

            //  editText 설정 해제
            year.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            month.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

            //  최소값 설정
            year.minValue = 2022
            month.minValue = 1
            //  최대값 설정
            year.maxValue = 2024
            month.maxValue = 12

            year.displayedValues = arrayOf("2022년","2023년","2024년")
            month.displayedValues = arrayOf("1월","2월","3월","4월","5월","6월","7월","8월","9월","10월",
                "11월","12월",)
            //  취소 버튼 클릭 시
            cancel.setOnClickListener {
                dialog.dismiss()
                dialog.cancel()
            }



            Log.d(ContentValues.TAG, "odos 날짜는 ${App.token_prefs.odosYear}, ${App.token_prefs.odosMonth}")
            //  완료 버튼 클릭 시
            save.setOnClickListener {
                odosText.text = "${year.value}년 ${month.value}월"

                dialog.dismiss()
                dialog.cancel()

                odosList.clear()
                prepare(binding, App.token_prefs.uid!!, odosApi, year.value, month.value)


            }

            dialog.setView(mView)
            dialog.create()
            dialog.show()



        }

    }


    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun prepare(
        binding: FragmentOdosBinding,
        uid: String,
        odosApi: OdosApi,
        year: Int,
        month: Int
    ) {

        retrofitGetAllOdos(odosApi, uid, year.toString(), month.toString())
    }

    fun retrofitGetAllOdos(odosApi: OdosApi, uid: String, year: String, month: String) {
        val callAllOdos = odosApi.getCallAllOdos(uid, year, month)
        resultGetAllOdos(callAllOdos)
    }

    fun resultGetAllOdos(callAllOdos: Call<GetResCallAllOdos>) {
        callAllOdos.enqueue(object : Callback<GetResCallAllOdos> {
            @RequiresApi(Build.VERSION_CODES.O)
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<GetResCallAllOdos>,
                response: Response<GetResCallAllOdos>
            ) {
                if (response.body()?.success == true) {
                    Log.d(ContentValues.TAG, "/odos get 성공 : ${response.body()}")

                    // 데이터 널값을 허용을 안한다.. 글이 하나도 없는 경우에는? 처음에;
                    val it = response.body()?.data!!
                    val eList = mutableListOf<String>()
                    val wList = mutableListOf<String>()



                    Log.d(TAG,"cntOdos: ${App.token_prefs.odosCnt}")
                    for (i in it.indices) {
                        eList.add(it[i].emotion)
                        wList.add(it[i].whether)
                        val data = OdosModel(it[i].content,it[i].createAt,it[i].emotion,it[i].whether)
                        Log.d(TAG,"data: $data")
                        odosList.add(data)

                    }
                    // odos개수 캐싱
                    App.token_prefs.odosCnt = it.size.toString()
                    // emotion 캐싱
                    App.token_prefs.emotionList = eList.distinct()
                    Log.d(TAG,"CntELIST:${App.token_prefs.emotionList}")
                    Log.d(TAG,"CntELISTCNT:${App.token_prefs.emotionList!!.size}")
                    // whether 캐싱
                    App.token_prefs.whetherList = wList.distinct()
                    Log.d(TAG,"CntWLIST:${App.token_prefs.whetherList}")
                    Log.d(TAG,"CntWLISTCNT:${App.token_prefs.whetherList!!.size}")

                    loop@ for (i in it.indices) {

                        if(it[i].createAt == LocalDate.now().toString()) {
                            // 이미 데이터 있는데 버튼 눌렀을 경우
                            binding.odosPlus.setOnClickListener {
                                Toast.makeText(context, "이미 odos를 작성하셨습니다!!", Toast.LENGTH_SHORT)
                                    .show()

                            }
                            break@loop
                        }

                        // 현재 시간과 동일한 날짜에 데이터가 이미 존재할 경우, 버튼 동작 x
                        if(it[i].createAt != LocalDate.now().toString() || it.isEmpty()){
                            Log.d(TAG,"data 함수 동작${it[i].createAt}")
                            Log.d(TAG,"data 함수 동작${LocalDate.now()}")
                            // OdosEditActivity로 이동
                            binding.odosPlus.setOnClickListener {
                                Log.d(TAG,"data 함수 동작22")
                                val odosPlusIntent = Intent(requireContext(), OdosEditActivity::class.java)
                                startActivity(odosPlusIntent)
                            }
                        }


                    }
                    binding.odosRecyclerView.adapter?.notifyDataSetChanged()




                } else {
                    Log.d(ContentValues.TAG, "/odos get 실패 : ${response.body()}")
                    binding.odosPlus.setOnClickListener {
                        Log.d(TAG,"data 실패시 버튼 활성화")
                        val odosPlusIntent = Intent(requireContext(), OdosEditActivity::class.java)
                        startActivity(odosPlusIntent)
                    }

                }
            }

            override fun onFailure(call: Call<GetResCallAllOdos>, t: Throwable) {
                Log.d(ContentValues.TAG, "/odos get 통신 에러: $t")
            }

        })
    }
}

/*
    val jsonData = """
        [
            {"content": "오늘 너무 덥다", "weather": "sunny", "emoji": "😓"},
            {"content": "내일은 비올 것 같아", "weather": "rainy", "emoji": "☔"},
            {"content": "좋은 날씨", "weather": "sunny", "emoji": "😎"},
            {"content": "한강 가기 좋은 날", "weather": "clear", "emoji": "🏞️"},
            {"content": "축구하기 딱 좋은 날", "weather": "cloudy", "emoji": "⚽"},
            {"content": "우산 필요 없는 날", "weather": "clear", "emoji": "😃"},
            {"content": "비올 때는 집에서 영화", "weather": "rainy", "emoji": "🎬"},
            {"content": "산책하기 좋은 날씨", "weather": "clear", "emoji": "🚶"},
            {"content": "오늘 너무 덥다", "weather": "sunny", "emoji": "😓"},
            {"content": "내일은 비올 것 같아", "weather": "rainy", "emoji": "☔"},
            {"content": "좋은 날씨", "weather": "sunny", "emoji": "😎"},
            {"content": "한강 가기 좋은 날", "weather": "clear", "emoji": "🏞️"},
            {"content": "축구하기 딱 좋은 날", "weather": "cloudy", "emoji": "⚽"},
            {"content": "우산 필요 없는 날", "weather": "clear", "emoji": "😃"},
            {"content": "비올 때는 집에서 영화", "weather": "rainy", "emoji": "🎬"},
            {"content": "산책하기 좋은 날씨", "weather": "clear", "emoji": "🚶"},
            {"content": "오늘 너무 덥다", "weather": "sunny", "emoji": "😓"},
            {"content": "내일은 비올 것 같아", "weather": "rainy", "emoji": "☔"},
            {"content": "좋은 날씨", "weather": "sunny", "emoji": "😎"},
            {"content": "한강 가기 좋은 날", "weather": "clear", "emoji": "🏞️"},
            {"content": "축구하기 딱 좋은 날", "weather": "cloudy", "emoji": "⚽"},
            {"content": "우산 필요 없는 날", "weather": "clear", "emoji": "😃"},
            {"content": "비올 때는 집에서 영화", "weather": "rainy", "emoji": "🎬"},
            {"content": "산책하기 좋은 날씨", "weather": "clear", "emoji": "🚶"},
            {"content": "오늘 너무 덥다", "weather": "sunny", "emoji": "😓"},
            {"content": "내일은 비올 것 같아", "weather": "rainy", "emoji": "☔"},
            {"content": "좋은 날씨", "weather": "sunny", "emoji": "😎"},
            {"content": "한강 가기 좋은 날", "weather": "clear", "emoji": "🏞️"},
            {"content": "축구하기 딱 좋은 날", "weather": "cloudy", "emoji": "⚽"},
            {"content": "우산 필요 없는 날", "weather": "clear", "emoji": "😃"},
            {"content": "비올 때는 집에서 영화", "weather": "rainy", "emoji": "🎬"},
            {"content": "산책하기 좋은 날씨", "weather": "clear", "emoji": "🚶"}
        ]
    """.trimIndent()

    val jsonArray = JSONArray(jsonData)
    for (i in 0 until jsonArray.length()) {
        val jsonObject: JSONObject = jsonArray.getJSONObject(i)
        val content = jsonObject.getString("content")
        val weather = jsonObject.getString("weather")
        val emoji = jsonObject.getString("emoji")

        val data = OdosModel(LocalDate.now(), "$content $weather $emoji")
        odosList.add(data)
    }

     */

