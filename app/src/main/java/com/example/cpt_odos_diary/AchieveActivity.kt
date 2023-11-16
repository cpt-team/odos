package com.example.cpt_odos_diary


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class AchieveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achieve)

        val recyclerView: RecyclerView = findViewById(R.id.achievement_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val AchievementList = generateAchievementList()

        val adapter = AchievementAdapter(AchievementList)
        recyclerView.adapter = adapter

        val achievementbackbtn = findViewById<ImageView>(R.id.iv_achievementback)
        achievementbackbtn.setOnClickListener {
            finish()
        }
    }

    private fun generateAchievementList(): List<Achievement> {
        val achievementList = mutableListOf<Achievement>()

        achievementList.add(Achievement("첫 다이어리 작성", "다이어리 작성 해보기", R.drawable.ic_achieve))
        achievementList.add(Achievement("첫 다이어리 작성", "다이어리 작성 해보기", R.drawable.ic_achieve))
        achievementList.add(Achievement("일주일 연속 다이어리 작성", "일주일 동안 매일 다이어리 작성하기", R.drawable.ic_achieve))
        achievementList.add(Achievement("긴급 상황에서 다이어리 작성", "긴급한 상황에서도 다이어리를 작성해보기", R.drawable.ic_achieve))
        achievementList.add(Achievement("다이어리 공유", "다이어리를 친구나 가족과 공유해보기", R.drawable.ic_achieve))
        achievementList.add(Achievement("오늘의 감사 일기 작성", "하루에 감사한 일을 기록하기", R.drawable.ic_achieve))
        achievementList.add(Achievement("다이어리 테마 변경", "다이어리의 테마를 바꿔보기", R.drawable.ic_achieve))
        achievementList.add(Achievement("다이어리에 그림 첨부", "다이어리에 그림이나 사진을 첨부해보기", R.drawable.ic_achieve))
        achievementList.add(Achievement("다이어리 카테고리 설정", "다이어리에 카테고리를 설정해보기", R.drawable.ic_achieve))
        achievementList.add(Achievement("다이어리 검색 활용", "다이어리 검색 기능을 활용하여 특정 일기를 찾아보기", R.drawable.ic_achieve))
        achievementList.add(Achievement("일기장 성취", "다양한 주제로 일기를 작성하여 일기장을 완성하기", R.drawable.ic_achieve))

        return achievementList

    }
}
