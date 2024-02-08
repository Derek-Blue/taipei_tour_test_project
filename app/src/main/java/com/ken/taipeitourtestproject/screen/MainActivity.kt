package com.ken.taipeitourtestproject.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ken.taipeitourtestproject.R
import com.ken.taipeitourtestproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}