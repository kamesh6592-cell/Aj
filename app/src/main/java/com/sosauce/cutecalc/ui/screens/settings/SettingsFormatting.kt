package com.ajstudioz.ajcalc.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.ajstudioz.ajcalc.R
import com.ajstudioz.ajcalc.data.datastore.rememberDecimal
import com.ajstudioz.ajcalc.data.datastore.rememberDecimalPrecision
import com.ajstudioz.ajcalc.ui.screens.settings.components.SettingsDropdownMenu
import com.ajstudioz.ajcalc.ui.screens.settings.components.SettingsSwitch
import com.ajstudioz.ajcalc.ui.screens.settings.components.SettingsWithTitle
import com.ajstudioz.ajcalc.ui.shared_components.CuteDropdownMenuItem
import com.ajstudioz.ajcalc.ui.shared_components.CuteNavigationButton
import com.ajstudioz.ajcalc.utils.formatNumber
import com.ajstudioz.ajcalc.utils.selfAlignHorizontally

@Composable
fun SettingsFormatting(onNavigateUp: () -> Unit) {
    val scrollState = rememberScrollState()
    var shouldFormat by rememberDecimal()
    var decimalPrecision by rememberDecimalPrecision()
    val decimalPrecisionOptions = MutableList(16) { it }.apply { add(1000) }

    Scaffold(
        bottomBar = {
            CuteNavigationButton(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .navigationBarsPadding()
                    .selfAlignHorizontally(Alignment.Start),
                onNavigateUp = onNavigateUp
            )
        }
    ) { pv ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(pv)
        ) {
            SettingsWithTitle(
                title = R.string.formatting
            ) {
                SettingsSwitch(
                    checked = shouldFormat,
                    onCheckedChange = { shouldFormat = !shouldFormat },
                    topDp = 24.dp,
                    bottomDp = 4.dp,
                    text = R.string.decimal_formatting
                )
                SettingsDropdownMenu(
                    value = decimalPrecision.toLong(),
                    topDp = 4.dp,
                    bottomDp = 24.dp,
                    text = R.string.decimal_precision,
                    optionalDescription = R.string.decimal_precision_desc
                ) {
                    decimalPrecisionOptions.fastForEach { number ->
                        CuteDropdownMenuItem(
                            onClick = { decimalPrecision = number },
                            text = { Text(number.toString().formatNumber(shouldFormat)) },
                            leadingIcon = {
                                RadioButton(
                                    selected = number == decimalPrecision,
                                    onClick = null
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
