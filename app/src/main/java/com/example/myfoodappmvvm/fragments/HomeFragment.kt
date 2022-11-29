package com.example.myfoodappmvvm.fragments

import android.content.Intent
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide


import com.example.myfoodappmvvm.activities.MealActivity
import com.example.myfoodappmvvm.databinding.FragmentHomeBinding
import com.example.myfoodappmvvm.pojo.Meal

import com.example.myfoodappmvvm.videoModel.HomeViewModel


class HomeFragment : Fragment() {
    //lien ket cac lop
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal:Meal

    companion object{
        //KHAI BAO CHO FRAGMENT CO ID, NAME
        const val MEAL_ID = "com.example.myfoodappmvvm.fragments.idMeal"
        const val MEAL_NAME = "com.example.myfoodappmvvm.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.myfoodappmvvm.fragments.thumbMeal"


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //khai bao Hommvvm lấy từ Homeviewmodel để rút gọn code
       homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //khai bao cho no chay thay vi su dụng return fragment_home
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //lý do 2 th này được tạo ra để ngắn gọn code chia từ homeviewmodel gắn sang home
        homeMvvm.getRandomMeal()
        // tạo hàm mới gắn gilde sau khi ấn vào nút home sẽ được random ảnh ở img_home
        observerRandomMeal()

        //khai bao ham moi sau khi click vao random meal se hien ra bai meal
        onRandomMealClick()
    }

    private fun onRandomMealClick() {
        binding.randomMealCardview.setOnClickListener {
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }

    }


    private fun observerRandomMeal() {
       homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner
       ) { meal ->
           Glide.with(this@HomeFragment)
               .load(meal!!.strMealThumb)
               .into(binding.imgRandomMeal)

           this.randomMeal = meal
       }
    }


}