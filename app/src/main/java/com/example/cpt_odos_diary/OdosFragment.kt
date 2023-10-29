package com.example.cpt_odos_diary

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cpt_odos_diary.adapter.MyAdapter
import com.example.cpt_odos_diary.adapter.OdosAdapter
import com.example.cpt_odos_diary.databinding.FragmentOdosBinding
import com.example.cpt_odos_diary.databinding.FragmentPlantGuideBinding
import com.example.cpt_odos_diary.model.OdosModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import java.time.LocalDate

class OdosFragment : Fragment() {
    lateinit var binding: FragmentOdosBinding
    private var odosList: MutableList<OdosModel> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentOdosBinding.inflate(inflater, container, false)
        val view = inflater.inflate(R.layout.fragment_odos, container, false)

        binding.odosRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.odosRecyclerView.adapter = OdosAdapter(activity as Context, odosList)
        prepare()


        // OdosEditActivity로 이동
        binding.odosPlus.setOnClickListener{
            val odosPlusIntent = Intent(requireContext(), OdosEditActivity::class.java)
            startActivity(odosPlusIntent)
        }


        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun prepare() {
        var data = OdosModel(LocalDate.now(), "hello")
        odosList.add(data)
        data = OdosModel(LocalDate.now(), "hello")
        odosList.add(data)
        data = OdosModel(LocalDate.now(), "hello")
        odosList.add(data)
        data = OdosModel(LocalDate.now(), "hello")
        odosList.add(data)
        data = OdosModel(LocalDate.now(), "hello")
        odosList.add(data)
        data = OdosModel(LocalDate.now(), "hello")
        odosList.add(data)
        data = OdosModel(LocalDate.now(), "hello")
        odosList.add(data)
        OdosAdapter(activity as Context, odosList)?.notifyDataSetChanged()

    }

}