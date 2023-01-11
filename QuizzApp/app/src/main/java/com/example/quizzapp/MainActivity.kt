package com.example.quizzapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startButton.setOnClickListener {
            if(etNameUser.text.toString().isEmpty()){
                Toast.makeText(this,"Enter your name please!",Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this,QuizQuestionActivity::class.java)
                intent.putExtra(Constants.USER_NAME,etNameUser.text.toString())
                startActivity(intent)
                finish()
            }

        }

    }
}