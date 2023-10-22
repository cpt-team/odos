package com.example.cpt_odos_diary

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class OdosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_odos, container, false)

        val odosButton = view.findViewById<Button>(R.id.button4)



        odosButton.setOnClickListener {
            val odosIntent = Intent(requireContext(), OdosListActivity::class.java)
            startActivity(odosIntent)
        }

        return view
    }

}