package com.example.happyplace.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.happyplace.activities.AddHappPlaceActivity
import com.example.happyplace.adapters.HappyPlacesAdapter
import com.example.happyplace.database.DatabaseHandler
import com.example.happyplace.databinding.ActivityMainBinding
import com.example.happyplace.models.HappyPlaceModel

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        binding?.fabAddHappyPlace?.setOnClickListener {
            Intent(this, AddHappPlaceActivity::class.java).apply {
                startActivityForResult(this, ADD_PLACE_ACTIVITY_REQUEST_CODE)
            }
        }
        setupHappyPlacesRecycleView(getHappyListFromLocalData())
    }

    private fun getHappyListFromLocalData(): ArrayList<HappyPlaceModel> {
        val dbHandler = DatabaseHandler(this)
        val getArrayList = dbHandler.getHappyPlacesList()
        if(getArrayList.size > 0) {
            binding?.rvHappyPlacesList?.visibility = View.VISIBLE
            binding?.tvNofound?.visibility = View.GONE
        } else {
            binding?.rvHappyPlacesList?.visibility = View.GONE
            binding?.tvNofound?.visibility = View.VISIBLE
        }
        return getArrayList
    }

    private fun setupHappyPlacesRecycleView(list: ArrayList<HappyPlaceModel>) {
        binding?.rvHappyPlacesList?.layoutManager = LinearLayoutManager(this)
        binding?.rvHappyPlacesList?.adapter = HappyPlacesAdapter(this, list)
        binding?.rvHappyPlacesList?.setHasFixedSize(true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Activity.RESULT_OK) {
            setupHappyPlacesRecycleView(getHappyListFromLocalData())
        }
    }
    companion object {
        val ADD_PLACE_ACTIVITY_REQUEST_CODE = 1
    }
}