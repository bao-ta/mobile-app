package com.example.workout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>) : RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {
    inner class ViewHolder(binding: ItemExerciseStatusBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvItem = binding.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].apply {
            holder.tvItem.text = this.getId().toString()
            when {
                this.getIsCompleted() -> {
                    holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.item_circular_color_accent_background)
                    holder.tvItem.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
                }
                this.getIsSelected() -> {
                    holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.item_circular_color_accent_border)

                } else -> {
                    holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.item_circular_color_gray_background)
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return items.size
    }


}