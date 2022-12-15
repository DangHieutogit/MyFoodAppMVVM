package com.example.myfoodappmvvm.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myfoodappmvvm.Adapter.CategoriesAdapter
import com.example.myfoodappmvvm.Adapter.MostPopularAdapter


import com.example.myfoodappmvvm.activities.MealActivity
import com.example.myfoodappmvvm.databinding.FragmentHomeBinding
import com.example.myfoodappmvvm.pojo.MealsByCategory
import com.example.myfoodappmvvm.pojo.Meal

import com.example.myfoodappmvvm.videoModel.HomeViewModel
import java.util.*


class HomeFragment : Fragment() {
    //lien ket cac lop
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal:Meal
    //phần 6
    private lateinit var popularItemsAdapter: MostPopularAdapter
    //P7
    private lateinit var categoriesAdapter: CategoriesAdapter

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

        popularItemsAdapter = MostPopularAdapter()
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

        //phan 6
        preparePopularItemsRecyclerView()

        //lý do 2 th này được tạo ra để ngắn gọn code chia từ homeviewmodel gắn sang home
        homeMvvm.getRandomMeal()
        // tạo hàm mới gắn gilde sau khi ấn vào nút home sẽ được random ảnh ở img_home
        observerRandomMeal()

        //khai bao ham moi sau khi click vao random meal se hien ra bai meal
        onRandomMealClick()

        //phần 6
        homeMvvm.getPopularItems()
        observerPopularItemsLiveData()
        //phan 6
        onPopularItemClick()

        //phần 7
        prepareCategoriesRecyclerView()
        homeMvvm.getCategories()
        observerCategoriesLiveData()



    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.RcvCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }

    private fun observerCategoriesLiveData() {
      homeMvvm.observeCategoriesLiveData().observe(viewLifecycleOwner, androidx.lifecycle.Observer { categories->
              categoriesAdapter.setCategoryList(categories)
      })
    }

    //sau khi an vao popular adapter se hien ra cac phan nay sẽ hiện ra màn hình MealActivity
    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    //phần 6
    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }
    }



    //phần 6
    private fun observerPopularItemsLiveData() {
        homeMvvm.observerPopularItemLiveData().observe(viewLifecycleOwner
        ) { mealList ->
            popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)
        }
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