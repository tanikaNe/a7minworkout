package com.gmail.weronikapios7.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.gmail.weronikapios7.a7minuteworkout.databinding.ActivityMainBinding
import java.lang.reflect.Type

//TODO use navigation component with fragments instead of activities
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        statusBarSettings()

    }

    private fun statusBarSettings(){
        //lay app behind system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)
        //TODO hide navigation bar
    }

}