package com.example.myfoodappmvvm.videoModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfoodappmvvm.pojo.*

import com.example.myfoodappmvvm.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel():ViewModel() {
    private  var randomMealLiveData = MutableLiveData<Meal>()
   //Phần 6
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    //#P7
    private var categoriesLiveData = MutableLiveData<List<Category>>()

    fun getRandomMeal(){
        //khai bao thuc hien RandomMeal ở cái bản img_random_meal cho nay chạy
        //gọi api gọi sử dụng object callback để đảm bảo đã được gọi thêm phần object

        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            //onRespone là khi kết nối thành công
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                //neu khai bao body random
                if (response.body() !=null){
                    val randomMeal: Meal = response.body()!!.meals[0] //trong listmeal chi có 1 val nen bắt đầu từ 0
                    //kiem tra sử dụng log d sau khi kiem tra xong thì phai báo Glide
                    //Log.d("TEST","Meal id ${randomMeal.idMeal} name ${randomMeal.strMeal}") //hiển thi trên img meal
                    randomMealLiveData.value =randomMeal

                }else{
                    return
                }
            }
            //onFailure là khi kết nối kh thành công
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                //neu sai
                Log.d("HomeFragment",t.message.toString())

            }
        })

    }
    //phần 6 call api popular
    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object :Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
               if (response.body() !=null){
                   popularItemsLiveData.value = response.body()!!.meals
               }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("HomeFragment",t.message.toString())
            }

        })
    }

    //#P7
    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { categoryList ->
                    categoriesLiveData.postValue(categoryList.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("HomeViewModel",t.message.toString())
            }

        })
    }


    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }
    //phần 6
    fun observerPopularItemLiveData():LiveData<List<MealsByCategory>>{
        return popularItemsLiveData
    }
    //phan 7
    fun observeCategoriesLiveData(): LiveData<List<Category>>{
        return  categoriesLiveData
    }
}