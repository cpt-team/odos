package com.example.cpt_odos_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View


class OdosEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_odos_edit)

    }
    fun onTvListButtonClick(view: View) {
        // MainActivity로 이동하는 Intent 생성
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        // MainActivity에서 OdosFragment로 전환
        (this as MainActivity).goToOdosFragment()
    }
}
