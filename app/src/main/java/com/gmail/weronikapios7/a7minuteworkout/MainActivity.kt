package com.gmail.weronikapios7.a7minuteworkout

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

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        statusBarSettings()
        binding.flStart.setOnClickListener {
            Toast.makeText(this, "working", Toast.LENGTH_SHORT).show()
        }




    }

    private fun statusBarSettings(){
        //lay app behind system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

    }
}