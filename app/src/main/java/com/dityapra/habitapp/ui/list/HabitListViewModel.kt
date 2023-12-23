package com.dityapra.habitapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.paging.PagedList
import com.dityapra.habitapp.R
import com.dityapra.habitapp.data.Habit
import com.dityapra.habitapp.data.HabitRepository
import com.dityapra.habitapp.utils.Event
import com.dityapra.habitapp.utils.HabitSortType

class HabitListViewModel(private val habitRepository: HabitRepository) : ViewModel() {
    private val _sort = MutableLiveData<HabitSortType>()
    private val _snackbarText = MutableLiveData<Event<Int>>()
    private val _undo = MutableLiveData<Event<Habit>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText
    val undo: LiveData<Event<Habit>> = _undo

    val habits: LiveData<PagedList<Habit>> = _sort.switchMap {
        habitRepository.getHabits(it)
    }

    init {
        _sort.value = HabitSortType.START_TIME
    }

    fun sort(sortType: HabitSortType) {
        _sort.value = sortType
    }

    fun deleteHabit(habit: Habit) {
        habitRepository.deleteHabit(habit)
        _snackbarText.value = Event(R.string.habit_deleted)
        _undo.value = Event(habit)
    }

    fun insert(habit: Habit) {
        habitRepository.insertHabit(habit)
    }
}
