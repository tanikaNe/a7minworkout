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

//TODO use navbar instead of activities
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)
        statusBarSettings()
        binding?.flStart?.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }




    }

    private fun statusBarSettings(){
        //lay app behind system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)
        //TODO hide navigation bar
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}