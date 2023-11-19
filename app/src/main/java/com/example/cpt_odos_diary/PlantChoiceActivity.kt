package com.example.cpt_odos_diary

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cpt_odos_diary.databinding.ActivityPlantChoiceBinding
import com.example.cpt_odos_diary.retrofit.GetResAllPlants
import com.example.cpt_odos_diary.retrofit.PlantApi
import com.example.cpt_odos_diary.retrofit.PlantList
import com.example.cpt_odos_diary.retrofit.RetrofitCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlantChoiceActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPlantChoiceBinding
    private var plnatList : MutableList<Plant> = mutableListOf()

    private val plnatApi: PlantApi = RetrofitCreator.plantApi

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPlantChoiceBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val callAllPlants = plnatApi.getAllPlant()
        getAllPlants(callAllPlants)
    }

    private fun getAllPlants(call: Call<GetResAllPlants>) {
        call.enqueue(object: Callback<GetResAllPlants>{
            override fun onResponse(call: Call<GetResAllPlants>, response: Response<GetResAllPlants>) {
                if(response.isSuccessful) {
                    Log.d(ContentValues.TAG, "식물 가져오기 성공 : ${response.body()}")
                    Log.d(ContentValues.TAG, "성공 message : ${response.body()?.message}")
                    Log.d(ContentValues.TAG, "성공 status : ${response.body()?.status}")
                    Log.d(ContentValues.TAG, "성공 success : ${response.body()?.success}")
                    Log.d(ContentValues.TAG, "데이터 : ${response.body()?.data}")

                    response.body()?.data?.let { dataPrepare(it) }
                    binding.plantChoiceViewpager.adapter = PlantAdapter(this@PlantChoiceActivity, plnatList)
                }
                else{
                    Log.d(ContentValues.TAG, "식물 가져오기 실패 : ${response.body()}")
                    Log.d(ContentValues.TAG, "실패 message : ${response.body()?.message}")
                    Log.d(ContentValues.TAG, "실패 status : ${response.body()?.status}")
                    Log.d(ContentValues.TAG, "실패 success : ${response.body()?.success}")
                }
            }
            override fun onFailure(call: Call<GetResAllPlants>, t: Throwable) {
                Log.d(ContentValues.TAG, "Get 실패 : $t")
            }
        })
    }
    private fun dataPrepare(data : List<PlantList>) {
        Log.d(ContentValues.TAG, data.size.toString())

        for(i in data.indices) {
            var plant = Plant(data[i].plantName, data[i].floriography, data[i].plantDesc)
            plnatList.add(plant)
        }
        binding.plantChoiceViewpager.adapter?.notifyDataSetChanged()
    }
}
