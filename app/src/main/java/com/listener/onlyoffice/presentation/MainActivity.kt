package com.listener.onlyoffice.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.listener.onlyoffice.DI
import com.listener.onlyoffice.R
import com.listener.onlyoffice.data.remote.retrofit.RetrofitStorage
import com.listener.onlyoffice.data.repository.AuthRepositoryImpl

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
    }
}