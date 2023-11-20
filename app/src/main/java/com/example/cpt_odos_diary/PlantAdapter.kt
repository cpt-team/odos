package com.example.cpt_odos_diary

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.cpt_odos_diary.App.App
import com.example.cpt_odos_diary.databinding.ItemPlantBinding
import com.example.cpt_odos_diary.retrofit.GetResAllPlants
import com.example.cpt_odos_diary.retrofit.PlantApi
import com.example.cpt_odos_diary.retrofit.PostReqPlantChoice
import com.example.cpt_odos_diary.retrofit.PostResPlantChoice
import com.example.cpt_odos_diary.retrofit.RetrofitCreator
import com.example.cpt_odos_diary.retrofit.RetrofitCreator.plantApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlantHolder(val binding: ItemPlantBinding) : RecyclerView.ViewHolder(binding.root)

class PlantAdapter(val context: Context, val data: MutableList<Plant>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val Api: PlantApi = RetrofitCreator.plantApi // 레트로핏 객체

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        PlantHolder(ItemPlantBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as PlantHolder).binding
        val plant = data[position]
        Log.d(ContentValues.TAG, "여기까진 오니?")

        // ItemPlant에 데이터 뿌려주기
        binding.plantName.text = plant.plantName
        binding.floriography.text = plant.floriography
        binding.plantDesc.text = plant.plantDesc

        binding.choiceButton.setOnClickListener{ // 해당 식물을 선택하면 db에 유저와 선택한 식물을 저장한다.
            // 식물 이름 : binding.plantName.text, 유저 id
            // 원래 유저의 uid는 App.token_prefs.uid에 들어있음 val uid: String? =  App.token_prefs.uid
            val uid = "6548a3408011beedd3a20868"
            val plantName: String = binding.plantName.text as String
            Log.d(ContentValues.TAG, plantName) // 잘 담겨 오는거 확인

            // 데이터 보내는 부분
            val reqData = PostReqPlantChoice(uid, plantName)
            val call = Api.postPlantChoice(reqData)
            postPlantChoice(call)

            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }

    }
    private fun postPlantChoice(call: Call<PostResPlantChoice>) {
        call.enqueue(object: Callback<PostResPlantChoice> {
            override fun onResponse(call: Call<PostResPlantChoice>, response: Response<PostResPlantChoice>) {
                Log.d(ContentValues.TAG, "Post 성공 : ${response.body()}")
                Log.d(ContentValues.TAG, "성공 message : ${response.body()?.message}")
                Log.d(ContentValues.TAG, "성공 status : ${response.body()?.status}")
                Log.d(ContentValues.TAG, "성공 success : ${response.body()?.success}")
                Log.d(ContentValues.TAG, "데이터 : ${response.body()?.data}")
            }
            override fun onFailure(call: Call<PostResPlantChoice>, t: Throwable) {
                Log.d(ContentValues.TAG, "Post 실패 : $t")
            }
        })
    }
}
