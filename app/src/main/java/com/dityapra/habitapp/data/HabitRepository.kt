package com.dityapra.habitapp.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dityapra.habitapp.utils.HabitSortType
import com.dityapra.habitapp.utils.SortUtils
import com.dityapra.habittracker.data.HabitDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HabitRepository(private val habitDao: HabitDao, private val executor: ExecutorService) {
    companion object {
        const val PAGE_SIZE = 20
        const val PLACEHOLDERS = true

        @Volatile
        private var instance: HabitRepository? = null

        fun getInstance(context: Context): HabitRepository {
            return instance ?: synchronized(this) {
                if (instance == null) {
                    val database = HabitDatabase.getInstance(context)
                    instance = HabitRepository(
                        database.habitDao(),
                        Executors.newSingleThreadExecutor()
                    )
                }
                return instance as HabitRepository
            }

        }
    }


    fun getHabits(sortType: HabitSortType): LiveData<PagedList<Habit>> {
        val sortedQuery = SortUtils.getSortedQuery(sortType)
        val factory = habitDao.getHabits(sortedQuery)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(PLACEHOLDERS)
            .setPageSize(PAGE_SIZE)
            .build()
        return LivePagedListBuilder(factory, config).build()
    }

    fun getHabitById(habitId: Int): LiveData<Habit> {
        return habitDao.getHabitById(habitId)
    }

    fun insertHabit(newHabit: Habit) {
        executor.execute {
            habitDao.insertHabit(newHabit)
        }
    }

    fun deleteHabit(habit: Habit) {
        executor.execute {
            habitDao.deleteHabit(habit)
        }
    }

    fun getRandomHabitByPriorityLevel(level: String): LiveData<Habit> {
        return habitDao.getRandomHabitByPriorityLevel(level)
    }
}
