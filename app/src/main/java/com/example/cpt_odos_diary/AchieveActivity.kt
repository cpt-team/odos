package com.example.cpt_odos_diary


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cpt_odos_diary.App.App


class AchieveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achieve)

    }

    override fun onResume() {
        super.onResume()
        val recyclerView: RecyclerView = findViewById(R.id.achievement_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val achievementList = generateAchievementList()

        val adapter = AchievementAdapter(achievementList)
        recyclerView.adapter = adapter

        val achievementBackBtn = findViewById<ImageView>(R.id.iv_achievementback)
        achievementBackBtn.setOnClickListener {
            finish()
        }
    }

}


    private fun generateAchievementList(): List<Achievement> {
        val diaryCnt = App.token_prefs.diaryCnt
        val odosCnt = App.token_prefs.odosCnt
        val emotionList = App.token_prefs.emotionList
        val whetherList = App.token_prefs.whetherList
        val achievementList = mutableListOf<Achievement>()


        if (diaryCnt != null) {
            if(diaryCnt.toInt() > 0){
                achievementList.add(Achievement("첫 다이어리 작성", "다이어리 작성 해보기", R.drawable.ic_achieve,true))
            }
            else if(diaryCnt.toInt() == 0){
                achievementList.add(Achievement("첫 다이어리 작성", "다이어리 작성 해보기", R.drawable.ic_achieve,false))
            }
        }

        if (odosCnt != null) {
            if(odosCnt.toInt() > 0){
                achievementList.add(Achievement("첫 odos 작성", "odos 작성 해보기", R.drawable.ic_achieve,true))
            }
            else if(odosCnt.toInt() == 0){
                achievementList.add(Achievement("첫 odos 작성", "odos 작성 해보기", R.drawable.ic_achieve,false))
            }
        }
        if (odosCnt != null && diaryCnt != null) {
            if((odosCnt.toInt()+diaryCnt.toInt()) >= 7){
                achievementList.add(Achievement("7일동안 나를 기록해요", "다이어리, odos 7개 작성", R.drawable.ic_achieve,true))
            }
            else if((odosCnt.toInt()+diaryCnt.toInt()) < 7){
                achievementList.add(Achievement("7일동안 나를 기록해요", "다이어리, odos 7개 작성", R.drawable.ic_achieve,false))
            }
        }

        if (emotionList != null) {
            if(emotionList.size == 5){
                achievementList.add(Achievement("내 감정에 솔직하기", "odos의 emotion 다 써보기", R.drawable.ic_achieve,true))
            }
            else if(emotionList.size < 5){
                achievementList.add(Achievement("내 감정에 솔직하기", "odos의 emotion 다 써보기", R.drawable.ic_achieve,false))
            }
        }
        if (whetherList != null) {
            if(whetherList.size == 5){
                achievementList.add(Achievement("너의 하루는?", "whether 다 써보기", R.drawable.ic_achieve,true))
            }
            else if(whetherList.size < 5){
                achievementList.add(Achievement("너의 하루는?", "whether 다 써보기", R.drawable.ic_achieve,false))
            }
        }
        if (odosCnt != null && diaryCnt != null) {
            if((odosCnt.toInt()+diaryCnt.toInt()) >= 100){
                achievementList.add(Achievement("기록 첫 기념일!!", "총 100일 작성", R.drawable.ic_achieve,true))
            }
            else if((odosCnt.toInt()+diaryCnt.toInt()) < 100){
                achievementList.add(Achievement("기록 첫 기념일!!", "총 100일 작성", R.drawable.ic_achieve,false))
            }
        }


        achievementList.add(Achievement("식물은 어떻게 성장하나요?", "식물 다음 단계로 성장시켜보기", R.drawable.ic_achieve,false))
        achievementList.add(Achievement("식물에 스킨 적용해보기", "식물의 스킨 적용해보기", R.drawable.ic_achieve,false))
        achievementList.add(Achievement("행복한 내 식물", "한 식물을 다 키워보기", R.drawable.ic_achieve,false))
        achievementList.add(Achievement("두 번째 선택", "두 번째 식물을 키워보기 ", R.drawable.ic_achieve,false))
        achievementList.add(Achievement("명언 마스터", "모든 명언 확인하기", R.drawable.ic_achieve,false))

        return achievementList
    }

