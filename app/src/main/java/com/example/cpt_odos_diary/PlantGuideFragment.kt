package com.example.cpt_odos_diary

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class PlantGuideFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_plant_guide, container, false)

        val guidButton = view.findViewById<Button>(R.id.button2)



        guidButton.setOnClickListener {
            val guideIntent = Intent(requireContext(), PlantGuideinfoActivity::class.java)
            startActivity(guideIntent)
        }

        return view
    }

}