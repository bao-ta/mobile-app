package com.example.workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    var binding: ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)


        setSupportActionBar(binding?.toolbarBmiActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //set back button
        supportActionBar?.title = "HISTORY" // Setting a title in the action bar.
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        val dao = (application as WorkoutApp).db.historyDao()

        lifecycleScope.launch {
            dao.fetchHistory().collect {
                val array = ArrayList<HistoryEntity>(it)
                getHistory(dao, array)
            }
        }

    }

    private fun getHistory(historyDao: HistoryDao, histories: ArrayList<HistoryEntity>) {


        if (histories.isNotEmpty()) {


            // Adapter class is initialized and list is passed in the param.
            val itemAdapter = HistoryAdapter(histories ) { date ->
                lifecycleScope.launch {
                    historyDao.findById(date).collect {
                        if(it != null) {
                            delete(it, historyDao)
                        }

                    }
                }

            }
            // Set the LayoutManager that this RecyclerView will use.
            binding?.rvItem?.layoutManager = LinearLayoutManager(this)
            // adapter instance is set to the recyclerview to inflate the items.
            binding?.rvItem?.adapter = itemAdapter
            binding?.rvItem?.visibility = View.VISIBLE
        } else {

            binding?.rvItem?.visibility = View.GONE

        }


    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun delete(date: HistoryEntity, dao: HistoryDao) {
        lifecycleScope.launch {
            dao.delete(date)
        }
    }


}