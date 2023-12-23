package com.dityapra.habitapp.ui.countdown

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat.getParcelableExtra
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.dityapra.habitapp.R
import com.dityapra.habitapp.data.Habit
import com.dityapra.habitapp.notification.NotificationWorker
import com.dityapra.habitapp.utils.HABIT
import com.dityapra.habitapp.utils.HABIT_ID
import com.dityapra.habitapp.utils.HABIT_TITLE
import com.dityapra.habitapp.utils.NOTIF_UNIQUE_WORK
import java.util.concurrent.TimeUnit

class CountDownActivity : AppCompatActivity() {
    private lateinit var workManager: WorkManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)
        supportActionBar?.title = "Count Down"

        val habit = getParcelableExtra(intent, HABIT, Habit::class.java)

        if (habit != null){
            findViewById<TextView>(R.id.tv_count_down_title).text = habit.title

            val viewModel = ViewModelProvider(this)[CountDownViewModel::class.java]

            viewModel.setInitialTime(habit.minutesFocus)

            viewModel.currentTimeString.observe(this) { currentTime ->
                findViewById<TextView>(R.id.tv_count_down).text = currentTime
            }

            viewModel.eventCountDownFinish.observe(this) { isFinished ->
                if (isFinished) {
                    updateButtonState(false)
                }
            }

            workManager = WorkManager.getInstance(this)

            findViewById<Button>(R.id.btn_start).setOnClickListener {
                viewModel.startTimer()
                updateButtonState(true)
                startNotificationWork(habit.id, habit.title, viewModel.getInitialTime())
            }

            findViewById<Button>(R.id.btn_stop).setOnClickListener {
                viewModel.resetTimer()
                updateButtonState(false)
                cancelNotificationWork()
            }
        }
    }

    private fun startNotificationWork(habitId: Int, habitTitle: String, delayDuration: Long?) {
        val inputData = Data.Builder()
            .putInt(HABIT_ID, habitId)
            .putString(HABIT_TITLE, habitTitle)
            .build()

        if (delayDuration != null) {
            val notificationWorkRequest = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
                .setInputData(inputData)
                .setInitialDelay(delayDuration, TimeUnit.MILLISECONDS)
                .addTag(NOTIF_UNIQUE_WORK)
                .build()
            workManager.enqueue(notificationWorkRequest)
        }
    }

    private fun cancelNotificationWork() {
        workManager.cancelAllWorkByTag(NOTIF_UNIQUE_WORK)
    }

    private fun updateButtonState(isRunning: Boolean) {
        findViewById<Button>(R.id.btn_start).isEnabled = !isRunning
        findViewById<Button>(R.id.btn_stop).isEnabled = isRunning
    }
}