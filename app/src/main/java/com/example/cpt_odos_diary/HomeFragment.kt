package com.example.cpt_odos_diary

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text


class HomeFragment : Fragment() {



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val achieveButton = view.findViewById<ImageView>(R.id.achievementButton)
        val settingButton = view.findViewById<ImageView>(R.id.settingButton)
        // "업적" 버튼을 클릭했을 때 AchieveActivity로 이동
        achieveButton.setOnClickListener {
            val achieveIntent = Intent(requireContext(), AchieveActivity::class.java)
            startActivity(achieveIntent)
        }

        // "설정" 버튼을 클릭했을 때 SettingActivity로 이동
        settingButton.setOnClickListener {
            val settingIntent = Intent(requireContext(), SettingActivity::class.java)
            startActivity(settingIntent)
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        val oneSentence = view?.findViewById<TextView>(R.id.oneSentence)
        oneSentence?.text = Setences()


    }


}


fun Setences(): String {
    val randNum = (Math.random()*10).toInt()

    val sentences = mutableListOf(
        "실패하는 것은 결국 성공으로 한발짝 걸었다는 것이다.",
        "항상 준비된 자에게만 기회가 온다.",
        "말을 하다보면 쓸데 없는 말이 나온다. 입은 닫을 수 있지만, 귀는 닫을 수 없다.",
        "너무 늦었거나 빠른 때는 없다. 그냥 지금이 가장 좋을 때다.",
        "사람은 어려움 속에서 성장한다.",
        "끝없이 인내하고 끝없이 노력하고 끝없이 겸손하자.",
        "삶에서 아무 문제도 갖고 있지 않은 사람은 이미 인생이란 경기에서 제외된 사람이다.",
        "당신이 자기 삶의 주인이 되지 않으면 다른 사람이 주인이 된다.",
        "시간은 인간이 쓸 수 있는 것 들 중에서 가장 소중한 것이다.",
        "삶이 당신에게 백 가지의 울어야 하는 이유를 던질 때, 천가지의 웃을 수 있는 이유를 보여줘라."
    )


    return sentences[randNum]

}