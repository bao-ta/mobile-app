package com.example.agecalculator

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var dateDisplay: TextView? = null
    private var minutesDisplay: TextView? = null
    private var daysDisplay: TextView? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        dateDisplay = findViewById(R.id.dateDisplay)
        minutesDisplay = findViewById(R.id.minutesDisplay)
        daysDisplay = findViewById(R.id.daysDisplay)
        btnDatePicker.setOnClickListener {
            clickDatePicker()

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,  {
                _, selectedyear, selectedmonth, selecteddayOfMonth ->
            Toast.makeText(
                this, "$selecteddayOfMonth - ${selectedmonth+1} - $selectedyear", Toast.LENGTH_SHORT
            ).show()
            val selectedDate = "$selecteddayOfMonth/${selectedmonth+1}/$selectedyear"
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

            val theDate = sdf.parse(selectedDate)
            theDate.let {

                val today = Date()
                val days = (today.time - theDate.time)/86400000
                val selectedDateMinute = theDate.time/6000
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                val currentDateMinutes = currentDate.time/6000
                val difMinute = currentDateMinutes - selectedDateMinute

                minutesDisplay?.text = difMinute.toString()
                dateDisplay?.text = selectedDate
                daysDisplay?.text = "$days"
            }


        }, year, month, day)
        dpd.datePicker.maxDate = System.currentTimeMillis() - 8640000
        dpd.show()

    }
}