package com.ajstudioz.ajcalc.data.actions

sealed interface CalcAction {
    data object GetResult : CalcAction
    data object ResetField : CalcAction
    data object Backspace : CalcAction
    data class AddToField(
        val value: String
    ) : CalcAction
}
