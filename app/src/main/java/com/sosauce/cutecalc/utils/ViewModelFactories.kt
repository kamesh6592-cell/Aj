package com.ajstudioz.ajcalc.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.ajstudioz.ajcalc.domain.repository.HistoryDatabase
import com.ajstudioz.ajcalc.ui.screens.calculator.CalculatorViewModel
import com.ajstudioz.ajcalc.ui.screens.history.HistoryViewModel


class HistoryViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    private val historyDb by lazy {
        Room.databaseBuilder(
            context = application,
            klass = HistoryDatabase::class.java,
            name = "history.db"
        ).build()
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HistoryViewModel(historyDb.dao) as T
    }
}

class CalculatorViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CalculatorViewModel(application) as T
    }
}
