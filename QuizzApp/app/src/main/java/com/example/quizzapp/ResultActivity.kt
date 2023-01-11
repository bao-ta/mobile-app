package com.example.quizzapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val name = intent.getStringExtra(Constants.USER_NAME).toString()
        myName.text = name
        var score = intent.getIntExtra(Constants.TOTAL_SCORE, 0)
        userScore.text = "Your score is $score out of 9"
        Log.i("baott", "zzzzzzzzzzzz: $name")
        replayButton.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }

        }

    }
}

