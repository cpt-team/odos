package com.example.cpt_odos_diary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cpt_odos_diary.R
import com.example.cpt_odos_diary.databinding.RecycleItemBinding
import com.example.cpt_odos_diary.model.Plantpedia

class MyViewHolder(val binding: RecycleItemBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapter(val context: Context, val data: MutableList<Plantpedia>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    // 뷰 홀더 객체 생성 부분
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = MyViewHolder(
        RecycleItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false))
    // 데이터 개수를 가져온다.
    override fun getItemCount(): Int = data.size
    // 리사이클러 뷰와 데이터 결합
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        // 데이터 등록
        val plant = data[position]
        // 뷰에 데이터 출력
        binding.plantImage.setImageResource(plant.plant_image)
        binding.plantName.text = plant.plant_name
        binding.growthRate.text = plant.growth_rate
        // 뷰에 이벤트 추가
        /*
        binding.overflow.setOnClickListener{
            showPopupMenu(binding.overflow)
            Toast.makeText(context, "Menu click", Toast.LENGTH_SHORT).show()
        }
         */
    }
    fun showPopupMenu(view: View) {
        val popup = PopupMenu(context, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.menu_menu, popup.menu)
    }

}