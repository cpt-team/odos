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
            R.drawable.gypsophila1,
            R.drawable.gypsophila2,
            R.drawable.gypsophila3,
            R.drawable.gypsophila4,
            R.drawable.gypsophila5,
            R.drawable.gypsophila6,
            R.drawable.gypsophila7,
            R.drawable.gypsophila8,
            R.drawable.gypsophila9,
            R.drawable.gypsophila10)
        var data = Plantpedia(plantimg[0], "gypsophila1", "50%")
        plantList.add(data)
        data = Plantpedia(plantimg[1], "gypsophila2", "90%")
        plantList.add(data)
        data = Plantpedia(plantimg[2], "gypsophila3","90%")
        plantList.add(data)
        data = Plantpedia(plantimg[3], "gypsophila4","90%")
        plantList.add(data)
        data = Plantpedia(plantimg[4], "gypsophila5","90%")
        plantList.add(data)
        data = Plantpedia(plantimg[5], "gypsophila6","90%")
        plantList.add(data)
        data = Plantpedia(plantimg[6], "gypsophila7","90%")
        plantList.add(data)
        data = Plantpedia(plantimg[7], "gypsophila8","90%")
        plantList.add(data)
        data = Plantpedia(plantimg[8], "gypsophila9","90%")
        plantList.add(data)
        data = Plantpedia(plantimg[9], "gypsophila10","80%")
        plantList.add(data)
        MyAdapter(activity as Context, plantList)?.notifyDataSetChanged()
    }
}