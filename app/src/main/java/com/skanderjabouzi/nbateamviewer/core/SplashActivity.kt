package com.skanderjabouzi.nbateamviewer.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.skanderjabouzi.nbateamviewer.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh)
        startActivity(MainActivity.getIntent(this))
    }
}
