package com.dityapra.habitapp.ui.random

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dityapra.habitapp.R
import com.dityapra.habitapp.data.Habit

class RandomHabitAdapter(
    private val onClick: (Habit) -> Unit
) : RecyclerView.Adapter<RandomHabitAdapter.PagerViewHolder>() {
    private val habitMap = LinkedHashMap<PageType, Habit>()
    private fun getIndexKey(position: Int) = habitMap.keys.toTypedArray().getOrNull(position)
    override fun getItemCount() = habitMap.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(key: PageType, habit: Habit) {
        habitMap[key] = habit
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PagerViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.pager_item, parent, false))

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val key = getIndexKey(position) ?: return
        val pageData = habitMap[key] ?: return
        holder.bind(key, pageData)
    }

    enum class PageType {
        HIGH, MEDIUM, LOW
    }

    inner class PagerViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.pager_tv_title)
        private val startTimeTextView: TextView = itemView.findViewById(R.id.pager_tv_start_time)
        private val priorityLevelImageView: ImageView = itemView.findViewById(R.id.item_priority_level)
        private val minutesTextView: TextView = itemView.findViewById(R.id.pager_tv_minutes)
        private val countDownButton: Button = itemView.findViewById(R.id.btn_open_count_down)

        fun bind(pageType: PageType, pageData: Habit) {
            titleTextView.text = pageData.title
            startTimeTextView.text = pageData.startTime
            minutesTextView.text = pageData.minutesFocus.toString()

            val colorPriority = when (pageType) {
                PageType.LOW -> R.drawable.ic_priority_low
                PageType.MEDIUM -> R.drawable.ic_priority_medium
                PageType.HIGH -> R.drawable.ic_priority_high
            }
            priorityLevelImageView.setImageResource(colorPriority)

            countDownButton.setOnClickListener {
                onClick(pageData)
            }
        }
    }
}
