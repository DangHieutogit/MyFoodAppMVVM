package com.example.myfoodappmvvm.videoModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.myfoodappmvvm.pojo.Meal
import com.example.myfoodappmvvm.pojo.MealList
import com.example.myfoodappmvvm.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel():ViewModel() {
    private  var randomMealLiveData = MutableLiveData<Meal>()

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

    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }
}