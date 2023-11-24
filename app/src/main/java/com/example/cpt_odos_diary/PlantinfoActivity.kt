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

        val adapter = PlantinfoAdapter(skinList)
        recyclerView.adapter = adapter

        val BackBtn = findViewById<ImageView>(R.id.iv_plantinfoback)
        BackBtn.setOnClickListener {
            finish()
        }

    }
}





    private fun generateMovieList(): List<PlantinfoModel> {
        val skinList = mutableListOf<PlantinfoModel>()

        skinList.add(PlantinfoModel("pinkpod", "odos의 emotion 다 써보기", R.drawable.pinkpod,"pod"))
        skinList.add(PlantinfoModel("yellowpod", "총 100일 작성", R.drawable.yellowpod,"pod"))
        skinList.add(PlantinfoModel("desert1", "첫 다이어리 작성하기", R.drawable.desert1,"back"))
        skinList.add(PlantinfoModel("desert2", "첫 odos 작성하기", R.drawable.desert2,"back"))
        skinList.add(PlantinfoModel("snow3", "whether 다 써보기", R.drawable.snow3,"back"))
        skinList.add(PlantinfoModel("snow5", "식물 다음 단계로 성장시켜보기", R.drawable.snow5,"back"))
        skinList.add(PlantinfoModel("snow7", "식물의 스킨 적용해보기", R.drawable.snow7,"back"))
        skinList.add(PlantinfoModel("greenpod", "한 식물을 다 키워보기", R.drawable.greenpod,"pod"))
        skinList.add(PlantinfoModel("field2", "두 번째 식물을 키워보기", R.drawable.field2,"back"))
        skinList.add(PlantinfoModel("field4", "다이어리, odos 7개 작성하기", R.drawable.field4,"back"))
        skinList.add(PlantinfoModel("snow8", "모든 명언 확인하기", R.drawable.snow8,"back"))


        return skinList
    }


