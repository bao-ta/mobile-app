package com.example.quizzapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_question.*
import kotlin.math.log

class QuizQuestionActivity : AppCompatActivity(), OnClickListener {
    var score = 0
    var currentIndex = 1
    val listQuestion = Constants.getListQuestion()
    var selectedOption = 0
    var nameUser: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)
        nameUser = intent.getStringExtra(Constants.USER_NAME).toString()
        Log.i("baott", "onCreate: $nameUser")
        setQuestion()

    }

    private fun setQuestion() {
        defaultOptionClick()
        questionNumber.text = "Question $currentIndex:"
        progressBar.progress = currentIndex
        textProgress.text = "${currentIndex}/9"
        image.setImageResource(listQuestion[currentIndex - 1].image)
        question.text = listQuestion[currentIndex - 1].content
        option1.text = listQuestion[currentIndex - 1].option1
        option2.text = listQuestion[currentIndex - 1].option2
        option3.text = listQuestion[currentIndex - 1].option3
        option4.text = listQuestion[currentIndex - 1].option4
        option1.setOnClickListener(this)
        option2.setOnClickListener(this)
        option3.setOnClickListener(this)
        option4.setOnClickListener(this)
        submitButton.setOnClickListener(this)
        if (currentIndex == 9) {
            submitButton.text = "Finish"
        } else {
            submitButton.text = "Submit"
        }
    }


    private fun answerView(answer: Int, drawableView: Int) {

        when (answer) {
            1 -> {
                option1.let {
                    it.background = ContextCompat.getDrawable(this, drawableView)
                }
            }
            2 -> {
                option2.let {
                    it.background = ContextCompat.getDrawable(this, drawableView)
                }
            }
            3 -> {
                option3.let {
                    it.background = ContextCompat.getDrawable(this, drawableView)
                }
            }
            4 -> {
                option4.let {
                    it.background = ContextCompat.getDrawable(this, drawableView)
                }
            }
        }
    }


    private fun defaultOptionClick() {
        val options = ArrayList<TextView>()
        option1.let {
            options.add(0, it)
        }
        option2.let {
            options.add(1, it)
        }
        option3.let {
            options.add(2, it)
        }
        option4.let {
            options.add(3, it)
        }
        for (otp in options) {
            otp.setTextColor(Color.parseColor("#7A8089"))
            otp.typeface = Typeface.DEFAULT
            otp.background = ContextCompat.getDrawable(this, R.drawable.default_option_border)

        }
    }

    private fun selectedOptionView(tv: TextView, selectedNum: Int) {
        defaultOptionClick()
        selectedOption = selectedNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.typeface = Typeface.DEFAULT_BOLD
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.option1 -> {
                option1.let {
                    selectedOptionView(it, 1)
                }
            }
            R.id.option2 -> {
                option2.let {
                    selectedOptionView(it, 2)
                }
            }
            R.id.option3 -> {
                option3.let {
                    selectedOptionView(it, 3)
                }
            }
            R.id.option4 -> {
                option4.let {
                    selectedOptionView(it, 4)
                }
            }
            R.id.submitButton -> {
                submitButton.let {

                    if (selectedOption == 0) {
                        currentIndex++
                        when {
                            currentIndex <= 9 -> {
                                setQuestion()
                            }
                            else -> {

                                val intent = Intent(this, ResultActivity::class.java)
                                intent.putExtra(Constants.USER_NAME,nameUser)
                                intent.putExtra(Constants.TOTAL_SCORE,score)
                                Log.i("baott", "submit: $nameUser")

                                startActivity(intent)
                                finish()
                            }
                        }

                    } else {
                        val question = listQuestion[currentIndex-1]
                        if(question.answer != selectedOption ) {
                            answerView(selectedOption, R.drawable.wrong_option_border)
                        } else {
                            ++score
                        }
                        answerView(question.answer, R.drawable.correct_option_border)

                        if(currentIndex == 9) {
                            it.text = "FINISHED"
                        } else {
                            it.text = "GO TO NEXT QUESTION"
                        }
                        selectedOption = 0
                    }

                }
            }
        }

    }
}