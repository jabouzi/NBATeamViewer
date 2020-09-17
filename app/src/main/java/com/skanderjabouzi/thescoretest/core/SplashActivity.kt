package com.skanderjabouzi.thescoretest.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skanderjabouzi.thescoretest.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh)

        startActivity(MainActivity.getIntent(this))
    }
}
