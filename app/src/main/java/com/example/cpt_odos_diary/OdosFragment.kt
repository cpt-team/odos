package com.example.cpt_odos_diary

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cpt_odos_diary.adapter.MyAdapter
import com.example.cpt_odos_diary.adapter.OdosAdapter
import com.example.cpt_odos_diary.databinding.FragmentOdosBinding
import com.example.cpt_odos_diary.databinding.FragmentPlantGuideBinding
import com.example.cpt_odos_diary.model.OdosModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate

class OdosFragment : Fragment() {
    lateinit var binding: FragmentOdosBinding
    private var odosList: MutableList<OdosModel> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentOdosBinding.inflate(inflater, container, false)
        val view = inflater.inflate(R.layout.fragment_odos, container, false)

        binding.odosRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.odosRecyclerView.adapter = OdosAdapter(activity as Context, odosList)
        prepare()


        // OdosEditActivity로 이동
        binding.odosPlus.setOnClickListener{
            val odosPlusIntent = Intent(requireContext(), OdosEditActivity::class.java)
            startActivity(odosPlusIntent)
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun prepare() {
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

        binding.odosRecyclerView.adapter?.notifyDataSetChanged()
    }

}