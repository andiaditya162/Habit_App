package com.dityapra.habitapp.ui.random

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.dityapra.habitapp.R
import com.dityapra.habitapp.ui.ViewModelFactory
import com.dityapra.habitapp.utils.HABIT
import com.dityapra.habitapp.ui.countdown.CountDownActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class RandomHabitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_habit)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val adapter = RandomHabitAdapter {habit ->
            val intent = Intent(this, CountDownActivity::class.java)
            intent.putExtra(HABIT, habit)
            startActivity(intent)
        }
        viewPager.adapter = adapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = "Habit ${position+1}"
        }.attach()

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[RandomHabitViewModel::class.java]

        viewModel.priorityLevelHigh.observe(this) {
            if (it != null){
                adapter.submitData(RandomHabitAdapter.PageType.HIGH, it)
            }
        }

        viewModel.priorityLevelMedium.observe(this) {
            if (it != null){
                adapter.submitData(RandomHabitAdapter.PageType.MEDIUM, it)
            }
        }

        viewModel.priorityLevelLow.observe(this) {
            if (it != null){
                adapter.submitData(RandomHabitAdapter.PageType.LOW, it)
            }
        }
    }
}
