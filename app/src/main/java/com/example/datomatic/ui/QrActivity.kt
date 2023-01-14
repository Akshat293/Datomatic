package com.example.datomatic.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.datomatic.R
import com.example.datomatic.databinding.ActivityDetailBinding
import com.example.datomatic.databinding.ActivityQrBinding

class QrActivity : AppCompatActivity() {
    private lateinit var binding:ActivityQrBinding
            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                binding = ActivityQrBinding.inflate(layoutInflater)
                setContentView(binding.root)

    }
}