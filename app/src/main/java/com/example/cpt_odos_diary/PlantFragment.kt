package com.example.cpt_odos_diary

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class PlantFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_plant, container, false)

        val plantButton = view.findViewById<Button>(R.id.button1)



        plantButton.setOnClickListener {
            val guideIntent = Intent(requireContext(), PlantinfoActivity::class.java)
            startActivity(guideIntent)
        }

        return view
    }

}