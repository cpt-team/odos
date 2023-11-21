package com.example.cpt_odos_diary

import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class AchievementAdapter(private val AchievementList: List<Achievement>) :
    RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder>() {

    class AchievementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.achievementcardView)
        val itemLayout : LinearLayout = itemView.findViewById(R.id.itemLayout)
        val coverImageView: ImageView = itemView.findViewById(R.id.coverImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.achievementTitle)
        val descTextView: TextView = itemView.findViewById(R.id.achievementDesc)
        val rewardSkins : ImageView = itemView.findViewById(R.id.rewardItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_achievement, parent, false)
        return AchievementViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val currentAchievement = AchievementList[position]
        Log.d(TAG,"achive CNT: ${AchievementList[position].isActive}, ${AchievementList[position].title}")
        holder.coverImageView.setImageResource(currentAchievement.coverResId)
        holder.titleTextView.text = currentAchievement.title
        holder.descTextView.text = currentAchievement.desc
        holder.rewardSkins.setImageResource(currentAchievement.rewardId)


        if (currentAchievement.isActive){
            holder.itemLayout.background = ColorDrawable(Color.GRAY)
        }
        if(!currentAchievement.isActive){
            holder.itemLayout.background = ColorDrawable(Color.WHITE)
        }

    }

    override fun getItemCount() = AchievementList.size
}