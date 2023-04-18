package com.example.workout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.workout.databinding.ActivityFinishedBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishedActivity : AppCompatActivity() {
    var binding: ActivityFinishedBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFinishedBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        binding?.button?.setOnClickListener {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }

        val dao = (application as WorkoutApp).db.historyDao()
        addDateToDatabase(dao)


    }


    private fun addDateToDatabase(historyDao: HistoryDao) {
        val c: Calendar = Calendar.getInstance()
        val dateTime = c.time
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)
        lifecycleScope.launch{
            historyDao.insert(HistoryEntity(date))
        }
    }
}