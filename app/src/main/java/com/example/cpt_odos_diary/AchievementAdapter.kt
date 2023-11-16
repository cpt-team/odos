package com.example.cpt_odos_diary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class AchievementAdapter(private val AchievementList: List<Achievement>) :
    RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder>() {

    class AchievementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.achievementcardView)
        val coverImageView: ImageView = itemView.findViewById(R.id.coverImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.achievementTitle)
        val descTextView: TextView = itemView.findViewById(R.id.achievementDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_achievement, parent, false)
        return AchievementViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val currentAchievement = AchievementList[position]
        holder.coverImageView.setImageResource(currentAchievement.coverResId)
        holder.titleTextView.text = currentAchievement.title
        holder.descTextView.text = currentAchievement.desc
    }

    override fun getItemCount() = AchievementList.size
}