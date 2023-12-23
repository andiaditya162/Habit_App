package com.dityapra.habitapp.ui.add

import androidx.lifecycle.ViewModel
import com.dityapra.habitapp.data.Habit
import com.dityapra.habitapp.data.HabitRepository

class AddHabitViewModel(private val habitRepository: HabitRepository) : ViewModel() {
    fun saveHabit(habit: Habit) {
        habitRepository.insertHabit(habit)
    }
}
