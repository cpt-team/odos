package com.example.cpt_odos_diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlantinfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plantinfo)

        val recyclerView: RecyclerView = findViewById(R.id.plantinfo_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val skinList = generateMovieList()

        val adapter = PlantinfoAdpater(skinList)
        recyclerView.adapter = adapter

        val BackBtn = findViewById<ImageView>(R.id.iv_plantinfoback)
        BackBtn.setOnClickListener {
            finish()
        }

    }

    private fun generateMovieList(): List<PlantinfoModel> {
        val skinList = mutableListOf<PlantinfoModel>()

        skinList.add(PlantinfoModel("분홍색 화분", "odos의 emotion 다 써보기", R.drawable.pinkpod))
        skinList.add(PlantinfoModel("노란색 화분", "총 100일 작성", R.drawable.yellowpod))
        skinList.add(PlantinfoModel("사막1", "첫 다이어리 작성하기", R.drawable.desert1))
        skinList.add(PlantinfoModel("사막2", "첫 odos 작성하기", R.drawable.desert2))
        skinList.add(PlantinfoModel("눈보라1", "whether 다 써보기", R.drawable.snow3))
        skinList.add(PlantinfoModel("눈보라2", "식물 다음 단계로 성장시켜보기", R.drawable.snow5))
        skinList.add(PlantinfoModel("눈보라3", "식물의 스킨 적용해보기", R.drawable.snow7))
        skinList.add(PlantinfoModel("초록색 화분", "한 식물을 다 키워보기", R.drawable.greenpod))
        skinList.add(PlantinfoModel("설산", "두 번째 식물을 키워보기", R.drawable.field2))
        skinList.add(PlantinfoModel("눈보라4", "모든 명언 확인하기", R.drawable.snow8))
        skinList.add(PlantinfoModel("무지개", "다이어리, odos 7개 작성하기", R.drawable.field4))

        return skinList
    }


}