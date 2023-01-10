package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var lastDot = false;
    var lastNumberic = false;
    var lastSubtract = false;
    private var textResult: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textResult = findViewById(R.id.textResult)

    }

    fun onDigit(view: View) {
        val button = view as Button
        textResult?.append(button.text)
        lastNumberic = true
        lastDot = false
    }

    fun onCLR(view: View) {
        textResult?.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (lastNumberic && !lastDot) {
            textResult?.append(".")
            lastDot = true
            lastNumberic = false
        }
    }

    fun onOperator(view: View) {
        textResult?.text.let {
            if (lastNumberic && !isAddedOpreator(it.toString()))
                textResult?.append((view as Button).text)
            lastNumberic = false
            lastDot = false
            lastSubtract = true
        }
    }

    private fun isAddedOpreator(value: String): Boolean {
        return (value.contains("+")
                || lastSubtract
                || value.contains("x")
                || value.contains("/"))

    }

    private fun removeZeroAfterDot(s: String): String {
        var value = s
        if (s.contains(".0")) {
            value = s.substring(0, s.length - 2)
        }
        return value
    }

    fun onEqual(view: View) {

        if (lastNumberic) {
            var textResultInput = textResult?.text.toString()
            try {
                var prefix = ""
                if (textResultInput.startsWith("-")) {
                    prefix = "-"
                    textResultInput = textResultInput.substring(1)
                }
                if (textResultInput.contains("-")) {
                    val splitValue = textResultInput.split("-")
                    var one = splitValue[0]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    val two = splitValue[1]
                    textResult?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if (textResultInput.contains("+")) {
                    val splitValue = textResultInput.split("+")
                    var one = splitValue[0]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    val two = splitValue[1]
                    textResult?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                } else if (textResultInput.contains("x")) {
                    val splitValue = textResultInput.split("x")
                    var one = splitValue[0]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    val two = splitValue[1]
                    textResult?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                } else if (textResultInput.contains("/")) {
                    val splitValue = textResultInput.split("/")
                    var one = splitValue[0]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    val two = splitValue[1]
                    textResult?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }
                lastSubtract = false

            } catch (e: ArithmeticException) {
                e.printStackTrace()

            }
        }

    }
}