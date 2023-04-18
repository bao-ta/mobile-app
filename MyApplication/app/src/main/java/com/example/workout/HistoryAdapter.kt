package com.example.workout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workout.databinding.ItemHistoryRowBinding

class HistoryAdapter(private val historyEntities: ArrayList<HistoryEntity>, private val delete:(date: String)->
Unit):
    RecyclerView
.Adapter<HistoryAdapter
.ViewHolder>
    () {
    class ViewHolder(binding: ItemHistoryRowBinding): RecyclerView.ViewHolder(binding.root) {
        val rvHistoryItemMain = binding.rvMain
        val tvDate = binding.tvDate
        val ivDelete = binding.ivDelete
        val tvNumber = binding.tvNumber
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHistoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = historyEntities[position]
        with(holder) {
            this.tvDate.text = item.date
            this.tvNumber.text = (position + 1).toString()
            this.ivDelete.setOnClickListener{
                delete(tvDate.text.toString())
            }
            if(position % 2 == 0) {
                holder.rvHistoryItemMain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color
                    .lightGrey))
            } else {
                holder.rvHistoryItemMain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color
                    .white))
            }
        }

    }

    override fun getItemCount(): Int {
        return historyEntities.size
    }


}