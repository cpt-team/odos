package com.example.cpt_odos_diary

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cpt_odos_diary.adapter.MyAdapter
import com.example.cpt_odos_diary.databinding.FragmentPlantGuideBinding
import com.example.cpt_odos_diary.model.Plantpedia

class PlantGuideFragment : Fragment() {
    lateinit var binding : FragmentPlantGuideBinding
    private var plantList: MutableList<Plantpedia> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPlantGuideBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = GridLayoutManager(activity, 2) // 레이아웃 메니저 설정. 그리드로 설정. 한줄에 2개씩만 보이도록.
        binding.recyclerView.adapter = MyAdapter(activity as Context, plantList)

        prepare()
        return binding.root
    }

    private fun prepare() {
        val plantimg = intArrayOf(
            R.drawable.chrysanthemum1,
            R.drawable.chrysanthemum2,
            R.drawable.chrysanthemum3,
            R.drawable.chrysanthemum4,
            R.drawable.chrysanthemum5,
            R.drawable.gypsophila_white1,
            R.drawable.gypsophila_white2,
            R.drawable.gypsophila_white3,
            R.drawable.gypsophila_white4,
            R.drawable.gypsophila_white5)
        var data = Plantpedia(plantimg[0], "국화 1단계", "20%")
        plantList.add(data)
        data = Plantpedia(plantimg[1], "국화 2단계", "40%")
        plantList.add(data)
        data = Plantpedia(plantimg[2], "국화 3단계","60%")
        plantList.add(data)
        data = Plantpedia(plantimg[3], "국화 4단계","80%")
        plantList.add(data)
        data = Plantpedia(plantimg[4], "국화 5단계","100%")
        plantList.add(data)
        data = Plantpedia(plantimg[5], "흰 안개꽃 1단계","20%")
        plantList.add(data)
        data = Plantpedia(plantimg[6], "흰 안개꽃 2단계","40%")
        plantList.add(data)
        data = Plantpedia(plantimg[7], "흰 안개꽃 3단계","60%")
        plantList.add(data)
        data = Plantpedia(plantimg[8], "흰 안개꽃 4단계","80%")
        plantList.add(data)
        data = Plantpedia(plantimg[9], "흰 안개꽃 5단계","100%")
        plantList.add(data)
        MyAdapter(activity as Context, plantList)?.notifyDataSetChanged()
    }
}