package com.example.cpt_odos_diary

import android.annotation.SuppressLint
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

        //retrofit
        val odosApi: OdosApi = RetrofitCreator.odosApi
        // req /diary에 사용할 데이터 변수 선언
        val uid: String? = App.token_prefs.uid
        // 현재 시간 출력 year,month -> String으로 서버에 넘겨야 하기 때문에 toString()
        val onlyDate: LocalDate = LocalDate.now() // 2023-11-17
        var cYear = onlyDate.year // 2023
        var cMonth = onlyDate.monthValue // 11

        Log.d(ContentValues.TAG, "날짜는 $cYear, $cMonth")




        if (uid != null) {
            prepare(binding, uid, odosApi, cYear, cMonth)
        }

        return binding.root
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
                    val it = response.body()?.data!!
                    for (i in it.indices) {
                        val data = OdosModel(it[i].content,it[i].createAt,it[i].emotion,it[i].whether)
                        Log.d(TAG,"data: $data")
                        odosList.add(data)
                        binding.odosRecyclerView.adapter?.notifyDataSetChanged()


                       // 현재 시간과 동일한 날짜에 데이터가 이미 존재할 경우, 버튼 동작 x
                        if(it[i].createAt != LocalDate.now().toString()){
                            // OdosEditActivity로 이동
                                binding.odosPlus.setOnClickListener {
                                val odosPlusIntent = Intent(requireContext(), OdosEditActivity::class.java)
                                startActivity(odosPlusIntent)
                            }
                        }
                        if(it[i].createAt == LocalDate.now().toString()){
                            // 이미 데이터 있는데 버튼 눌렀을 경우
                            binding.odosPlus.setOnClickListener {
                                Toast.makeText(context, "이미 odos를 작성하셨습니다!!", Toast.LENGTH_SHORT).show()
                            }
                        }



                    }


                } else {
                    Log.d(ContentValues.TAG, "/odos get 실패 : ${response.body()}")

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

