package com.example.cpt_odos_diary

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
//import com.example.cpt_odos_diary.App.App
import com.example.cpt_odos_diary.databinding.ActivityMainBinding


private const val TAG_Plant = "Plant_Fragment"
private const val TAG_Guide = "Guide_Fragment"
private const val TAG_HOME = "Home_fragment"
private const val TAG_Odos = "Odos_Fragment"
private const val TAG_Diary = "Diary_Fragment"

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.navigationView.selectedItemId = R.id.tabhome
        setFragment(TAG_HOME, HomeFragment())


        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.tabplant -> setFragment(TAG_Plant, PlantFragment())
                R.id.tabguide -> setFragment(TAG_Guide, PlantGuideFragment())
                R.id.tabhome -> setFragment(TAG_HOME, HomeFragment())
                R.id.tabodos -> setFragment(TAG_Odos, OdosFragment())
                R.id.tabdiary-> setFragment(TAG_Diary, DiaryFragment())
            }
            true
        }


    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }

        val plant = manager.findFragmentByTag(TAG_Plant)
        val guide = manager.findFragmentByTag(TAG_Guide)
        val home = manager.findFragmentByTag(TAG_HOME)
        val odos = manager.findFragmentByTag(TAG_Odos)
        val diary = manager.findFragmentByTag(TAG_Diary)

        if (plant != null){
            fragTransaction.hide(plant)
        }

        if (guide != null){
            fragTransaction.hide(guide)
        }

        if (home != null) {
            fragTransaction.hide(home)
        }

        if (odos != null) {
            fragTransaction.hide(odos)
        }

        if (diary != null) {
            fragTransaction.hide(diary)
        }

        if (tag == TAG_Plant) {
            if (plant!=null){
                fragTransaction.show(plant)
            }
        }
        else if (tag == TAG_Guide) {
            if (guide != null) {
                fragTransaction.show(guide)
            }
        }

        else if (tag == TAG_HOME){
            if (home != null){
                fragTransaction.show(home)
            }
        }

        else if (tag == TAG_Odos){
            if (odos != null){
                fragTransaction.show(odos)
            }
        }

        else if (tag == TAG_Diary){
            if (diary != null){
                fragTransaction.show(diary)
            }
        }

        fragTransaction.commitAllowingStateLoss()
        // App.token_prefs.accessToken?.let { it1 -> Log.d(ContentValues.TAG, "토큰: $it1") }
    }

}

