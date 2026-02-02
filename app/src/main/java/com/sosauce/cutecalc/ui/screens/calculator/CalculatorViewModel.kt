package com.ajstudioz.ajcalc.ui.screens.calculator

import android.app.Application
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ajstudioz.ajcalc.data.actions.CalcAction
import com.ajstudioz.ajcalc.data.calculator.Evaluator
import com.ajstudioz.ajcalc.data.datastore.getDecimalPrecision
import com.ajstudioz.ajcalc.utils.backspace
import com.ajstudioz.ajcalc.utils.insertText
import com.ajstudioz.ajcalc.utils.isErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CalculatorViewModel(
    private val application: Application
) : AndroidViewModel(application) {


    val textFieldState = TextFieldState()
    var evaluatedCalculation by mutableStateOf("")
        private set

    private val _previewShowErrors = MutableStateFlow(false)
    val previewShowErrors = _previewShowErrors.asStateFlow()


    init {
        viewModelScope.launch {
            snapshotFlow { textFieldState.text.toString() }
                .collectLatest { text ->
                    val decimalPrecision =
                        getDecimalPrecision(application.applicationContext).first()
                    evaluatedCalculation = if (textFieldState.text.isEmpty()) {
                        // there's currently a bug that will keep the preview to the last result even if this is empty, my head hurts too much to search a real fix atm
                        ""
                    } else {
                        Evaluator.eval(text, decimalPrecision)
                    }
                }
        }
    }

    fun handleAction(action: CalcAction) {
        _previewShowErrors.update { false }

        when (action) {
            is CalcAction.GetResult -> {
                if (evaluatedCalculation.isErrorMessage()) {
                    _previewShowErrors.update { true }
                } else {
                    textFieldState.setTextAndPlaceCursorAtEnd(evaluatedCalculation)
                }
            }

            is CalcAction.AddToField -> textFieldState.insertText(action.value)
            is CalcAction.ResetField -> textFieldState.clearText()
            is CalcAction.Backspace -> textFieldState.backspace()
        }
    }

}
