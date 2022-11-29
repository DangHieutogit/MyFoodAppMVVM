package com.example.myfoodappmvvm.retrofit

import com.example.myfoodappmvvm.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MealApi {

    @GET("random.php")
    //url cơ sở xem trong file word
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id:String) : Call<MealList>
}