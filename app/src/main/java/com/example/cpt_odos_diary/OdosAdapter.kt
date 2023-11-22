package com.example.cpt_odos_diary.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cpt_odos_diary.OdosModel
import com.example.cpt_odos_diary.R
import com.example.cpt_odos_diary.databinding.OdosItemBinding

class OdosViewHolder(val binding: OdosItemBinding) : RecyclerView.ViewHolder(binding.root)

class OdosAdapter(val context: Context, val data: MutableList<OdosModel>) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        OdosViewHolder(OdosItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as OdosViewHolder).binding
        val odos = data[position]
        Log.d(TAG,"data odos: $odos")
        val day = odos.createAt.split("-")[2]
        binding.content.text = "[${day}일] ${odos.content}"
        val emotion: Int = when (odos.emotion) {
            "happy" -> R.drawable.smiling
            "love" -> R.drawable.love
            "sleepy" -> R.drawable.sleep
            "sad" -> R.drawable.crying
            "fine" -> R.drawable.wow
            "angry" -> R.drawable.angry
            else -> {R.drawable.love_gray}
        }
        binding.emotionImage.setImageResource(emotion)
        val whether : Int = when(odos.whether){
            "sunny" -> R.drawable.img_sun
            "sunnyCloudy" -> R.drawable.img_cloudy
            "windy" -> R.drawable.img_cloud
            "cloudy" -> R.drawable.img_bad_cloud
            "rainy" -> R.drawable.img_rainy
            "snowy" -> R.drawable.img_snowy
            else -> {R.drawable.img_sun_gray}
        }

        binding.weatherImage.setImageResource(whether)
    }
}