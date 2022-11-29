package com.example.myfoodappmvvm.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {
    //object cần có để xây dựng phiên bản trang bị thêm của MealApi trong Retrofit
    //hoac su dung 1 bien bth
    // lateinit var api: MealApi
    //khai bao val api boi bylazy se thuận tiện ơi
 val api:MealApi by lazy {
     Retrofit.Builder()
         //lay Url cơ sở sau của trang đấy
         .baseUrl("https://www.themealdb.com/api/json/v1/1/")
             //chuyen doi no thanh doi tuong kotlin
         .addConverterFactory(GsonConverterFactory.create())
        //và chúng ta cần xây dựng cái này
         .build()
         .create(MealApi::class.java)
 }




}