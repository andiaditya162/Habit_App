package com.dityapra.habitapp.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dityapra.habitapp.R
import com.dityapra.habitapp.data.Habit
import java.util.Locale

class HabitAdapter(
    private val onClick: (Habit) -> Unit
) : PagedListAdapter<Habit, HabitAdapter.HabitViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.habit_item, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        getItem(position)?.let { habit ->
            holder.bind(habit)
        }
    }

    inner class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.item_tv_title)
        private val ivPriority: ImageView = itemView.findViewById(R.id.item_priority_level)
        private val tvStartTime: TextView = itemView.findViewById(R.id.item_tv_start_time)
        private val tvMinutes: TextView = itemView.findViewById(R.id.item_tv_minutes)
        lateinit var getHabit: Habit

        fun bind(habit: Habit) {
            getHabit = habit
            tvTitle.text = habit.title
            tvStartTime.text = habit.startTime
            tvMinutes.text = habit.minutesFocus.toString()
            val priorityLevel = when (habit.priorityLevel.lowercase(Locale.ROOT)) {
                "high" -> R.drawable.ic_priority_high
                "medium" -> R.drawable.ic_priority_medium
                "low" -> R.drawable.ic_priority_low
                else -> R.drawable.ic_priority_low
            }

            ivPriority.setImageResource(priorityLevel)
            itemView.setOnClickListener {
                onClick(habit)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Habit>() {
            override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
                return oldItem == newItem
            }
        }
    }
}