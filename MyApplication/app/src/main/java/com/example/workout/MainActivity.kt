package com.example.workout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.workout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding?.root)
        binding?.flLayoutProcessbar?.setOnClickListener {
            Intent(this, ExerciseActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding?.flLayoutBmi?.setOnClickListener {
            Intent(this, BMIActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding?.flLayoutHistory?.setOnClickListener {
            Intent(this, HistoryActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}