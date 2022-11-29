package com.example.myfoodappmvvm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.myfoodappmvvm.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //khai bao gan kieu
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.btn_nav)
        val navController = Navigation.findNavController(this, R.id.hots_fragment)

        /* kh hieu sao cai nay co thể làm chuyển được màn hình bottomNavigation
        khi ấn vào các icon có thể được chuyến đến fragment tương tư như
        Nhấn home sẽ được chuyển qua màn hình home nhân favorites sẽ được qua
        màn hình xml fragment favorites có thể là khi ta gán id ở item nên sẽ được
        chuyển qua chuyền lại giữa id như item home chứa id home item favorites đang
         được chứa id của fragment favorites
        */
        NavigationUI.setupWithNavController(bottomNavigation,navController)


    }
}