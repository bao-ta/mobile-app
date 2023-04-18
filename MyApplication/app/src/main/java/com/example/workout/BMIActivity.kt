package com.example.workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.workout.databinding.ActivityBmiactivityBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    companion object {
        private const val METRIC_UNIT_VIEW = "METRIC UNIT VIEW"
        private const val US_UNIT_VIEW = "US UNIT VIEW"
    }

    var currentUnit: String = BMIActivity.METRIC_UNIT_VIEW
    var binding: ActivityBmiactivityBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //set back button
        supportActionBar?.title = "CALCULATE BMI" // Setting a title in the action bar.
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.tvDetail?.text = Constants.getDetail()
        binding?.btnCalculateUnits?.setOnClickListener {
            when (currentUnit) {
                METRIC_UNIT_VIEW -> {
                    if (validateMetricUnit()) {
                        val height: Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100
                        val weight: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()
                        val bmi = weight / (height * height)
                        displayBMIResult(bmi)
                    } else {
                        Toast.makeText(this, "fill all the blank", Toast.LENGTH_SHORT).show()
                    }
                }
                US_UNIT_VIEW -> {
                    if (validateUSUnit()) {
                        val weight: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()
                        val inch: Float = binding?.etMetricUnitInch?.text.toString().toFloat()
                        val feet: Float = binding?.etMetricUnitFeet?.text.toString().toFloat()
                        val height = inch + feet * 12
                        val bmi = 703 * (weight / (height * height))
                        displayBMIResult(bmi)
                    } else {
                        Toast.makeText(this, "fill all the blank", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        binding?.rbMetricUnits?.setOnClickListener {
            makeMetricUnitViewVisible()
        }
        binding?.rbUsUnits?.setOnClickListener {
            makeUSUnitViewVisible()
        }


    }

    private fun validateMetricUnit(): Boolean {
        if (binding?.etMetricUnitHeight?.text?.toString()?.isEmpty() == true ||
            binding?.etMetricUnitWeight?.text?.toString()?.isEmpty() == true
        ) {
            return false
        }
        return true
    }

    private fun validateUSUnit(): Boolean {
        if (binding?.etMetricUnitFeet?.text?.toString()?.isEmpty() == true ||
            binding?.etMetricUnitInch?.text?.toString()?.isEmpty() == true ||
            binding?.etMetricUnitWeight?.text?.toString()?.isEmpty() == true
        ) {
            return false
        }
        return true
    }

    private fun makeUSUnitViewVisible() {
        if (currentUnit == BMIActivity.METRIC_UNIT_VIEW) {
            binding?.tilMetricUnitWeight?.hint = "WEIGHT(in pound)"
            currentUnit = BMIActivity.US_UNIT_VIEW
            binding?.etMetricUnitWeight?.text?.clear()
            binding?.etMetricUnitHeight?.text?.clear()
            binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE
            binding?.llUSUnit?.visibility = View.VISIBLE
        }
    }

    private fun makeMetricUnitViewVisible() {
        if (currentUnit == BMIActivity.US_UNIT_VIEW) {
            binding?.tilMetricUnitWeight?.hint = "WEIGHT(in kg)"
            currentUnit = BMIActivity.METRIC_UNIT_VIEW
            binding?.etMetricUnitWeight?.text?.clear()
            binding?.etMetricUnitFeet?.text?.clear()
            binding?.etMetricUnitInch?.text?.clear()
            binding?.llUSUnit?.visibility = View.INVISIBLE
            binding?.etMetricUnitHeight?.visibility = View.VISIBLE
            binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        }
    }

    private fun displayBMIResult(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        //Use to set the result layout visible
        binding?.llDiplayBMIResult?.visibility = View.VISIBLE

        // This is used to round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.tvBMIValue?.text = bmiValue // Value is set to TextView
        binding?.tvBMIType?.text = bmiLabel // Label is set to TextView
        binding?.tvBMIDescription?.text = bmiDescription // Description is set to TextView
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}