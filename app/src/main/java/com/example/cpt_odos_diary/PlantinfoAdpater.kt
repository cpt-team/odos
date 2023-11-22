package com.example.cpt_odos_diary

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class PlantinfoAdpater (private val SkinList: List<PlantinfoModel>) :
    RecyclerView.Adapter<PlantinfoAdpater.PlantinfoViewHolder>(){


    class PlantinfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.plantinfocardView)
        val itemLayout : LinearLayout = itemView.findViewById(R.id.plantitemLayout)
        val coverImageView: ImageView = itemView.findViewById(R.id.iv_plantinfo)
        val titleTextView: TextView = itemView.findViewById(R.id.plantinfoTitle)
        val descTextView: TextView = itemView.findViewById(R.id.plantinfoDesc)
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        val plantswitch: Switch = itemView.findViewById(R.id.plantinfoSwitch)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantinfoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plantinfo, parent, false)
        return PlantinfoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlantinfoViewHolder, position: Int) {
        val currentSkin = SkinList[position]
        holder.coverImageView.setImageResource(currentSkin.coverResId)
        holder.titleTextView.text = currentSkin.title
        holder.descTextView.text = currentSkin.desc


        // 스위치에 대한 리스너 설정
        updateItemView(holder, holder.plantswitch.isChecked)
        holder.plantswitch.setOnCheckedChangeListener { _, isChecked ->
            updateItemView(holder, isChecked)
        }
    }

    private fun updateItemView(holder: PlantinfoViewHolder, isChecked: Boolean) {
        if (isChecked) {
            // 스위치가 켜진 경우
            holder.cardView.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.originalCardColor)
            )
        } else {
            // 스위치가 꺼진 경우
            holder.cardView.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.grayedOutCardColor)
            )
        }
    }

    override fun getItemCount() =SkinList.size

}