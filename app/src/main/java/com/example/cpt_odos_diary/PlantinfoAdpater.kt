package com.example.cpt_odos_diary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class PlantinfoAdpater (private val PlantinfoList: List<PlantinfoModel>) :
    RecyclerView.Adapter<PlantinfoAdpater.PlantinfoViewHolder>(){


    class PlantinfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.plantinfocardView)
        val itemLayout : LinearLayout = itemView.findViewById(R.id.plantitemLayout)
        val coverImageView: ImageView = itemView.findViewById(R.id.iv_plantinfo)
        val titleTextView: TextView = itemView.findViewById(R.id.plantinfoTitle)
        val descTextView: TextView = itemView.findViewById(R.id.plantinfoDesc)
        val plantswitch: SwitchCompat = itemView.findViewById(R.id.plantinfoSwitch)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantinfoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plantinfo, parent, false)
        return PlantinfoViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: PlantinfoViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}