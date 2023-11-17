package com.example.cpt_odos_diary.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cpt_odos_diary.OdosModel
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
        binding.content.text = odos.content

    }
}