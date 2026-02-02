package com.ajstudioz.ajcalc.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajstudioz.ajcalc.domain.model.Calculation
import com.ajstudioz.ajcalc.domain.repository.HistoryDao
import com.ajstudioz.ajcalc.domain.repository.HistoryEvents
import com.ajstudioz.ajcalc.utils.isErrorMessage
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val dao: HistoryDao,
) : ViewModel() {

    val allCalculations = dao.getAllCalculations()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun onEvent(event: HistoryEvents) {
        when (event) {
            is HistoryEvents.AddCalculation -> {

                val calculation = Calculation(
                    operation = event.operation,
                    result = event.result
                )

                if (event.saveErrors || !event.result.isErrorMessage()) {
                    viewModelScope.launch {
                        if (allCalculations.value.size.toLong() == event.maxHistoryItems) {
                            dao.deleteCalculation(allCalculations.value.first())
                        }
                        dao.insertCalculation(calculation)
                    }
                } else {
                    return
                }

            }

            is HistoryEvents.DeleteCalculation -> {
                viewModelScope.launch { dao.deleteCalculation(event.calculation) }
            }

            is HistoryEvents.DeleteAllCalculation -> {
                viewModelScope.launch { dao.deleteAllCalculations() }
            }
        }
    }
}
