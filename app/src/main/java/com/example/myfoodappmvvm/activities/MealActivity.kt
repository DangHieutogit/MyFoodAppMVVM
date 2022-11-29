package com.example.myfoodappmvvm.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myfoodappmvvm.R
import com.example.myfoodappmvvm.databinding.ActivityMealBinding
import com.example.myfoodappmvvm.fragments.HomeFragment
import com.example.myfoodappmvvm.pojo.Meal
import com.example.myfoodappmvvm.videoModel.MealViewModel

class MealActivity : AppCompatActivity() {
    //tao cac bien

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var mealMvvm:MealViewModel
    private lateinit var binding: ActivityMealBinding
    private lateinit var youtubeLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        //khai bao man binding
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm = ViewModelProvider(this)[MealViewModel::class.java]

        getMealInformationFromIntent()

        setInfomationInViews()
        //khi bat dau vao trag se load
        loadingCase()

        mealMvvm.getMealDetail(mealId)
        observerMealDeatialsLiveData()

        //img cua onlclick utube
        onYoutubeImageClick()
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    //thông tin món ăn và địa chỉ
    private fun observerMealDeatialsLiveData() {
        mealMvvm.observerMealDetailsLiveData().observe(this,object :Observer<Meal>{
            override fun onChanged(t: Meal?) {
                onResponsecase()
                //lay du lieu cate and Area
                val meal = t

                binding.tvCategory.text = "Category: ${meal!!.strCategory}"
                binding.tvArea.text = "Area: ${meal!!.strArea}"
                binding.tvInstructionsSt.text = meal.strInstructions

                youtubeLink= meal.strYoutube

            }

        })
    }
    //thông tin ảnh
    private fun setInfomationInViews() {
        Glide.with(applicationContext)
                /*goi thong tin in view sau khi load nhan vao imgMealDetail tu man hinh
                HomeFragment se duoc truyen den man hinh mealActivity tu thumb va img
                */
            .load(mealThumb)
            .into(binding.imgMealDetail)
        //lay name id and img truyen sang th mealActivity
        binding.collapingToolbar.title = mealName
        //set color cho title la mau trang
        binding.collapingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

    }

    //lay ham tu homeFragment call sau khi click se hien ra thong tin mon
    private fun getMealInformationFromIntent() {
        //lay id name thumb tu ben homeFragment truyen vao mealactivity
        val intent = intent
        mealId= intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb= intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE

    }
    //sau khi ket noi duoc se xuat hien trong observerMealDetailLiveData
    private fun onResponsecase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE

    }
}