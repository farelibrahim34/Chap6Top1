package com.example.chap6top1.profile


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.activity.viewModels

import com.example.chap6top1.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity() {
    lateinit var binding : ActivityProfileBinding
    private val viewModelProfile : BlurProfileViewModel by viewModels { BlurViewModelFactoryProfile(application) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUpdate.setOnClickListener {
            viewModelProfile.applyBlur(2)
        }



    }

}