package com.example.hostelaccountiong2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hostelaccountiong2.mechanics.Buying
import com.example.hostelaccountiong2.mechanics.BuyingManager
import com.example.hostelaccountiong2.sheets.AbstractTableManager

@Composable
fun AbstractTableManager.Settings() {
    var allSeasons by remember { mutableStateOf(getAllSeasons()) }
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                text = "Текущий сезон",
                style = MaterialTheme.typography.titleLarge
            )
            DisplaySeason(allSeasons.last(), ended = false, active = true) {
                allSeasons = it
            }
            Text(
                text = "Завершённые сезоны",
                style = MaterialTheme.typography.titleLarge
            )
            allSeasons.dropLast(1).reversed().forEach {
                DisplaySeason(it, ended = true, active = false)
            }
        }
    }
}

@Composable
private fun AbstractTableManager.DisplaySeason(
    season: List<Buying>,
    ended: Boolean,
    active: Boolean,
    modifyAllSeasons: (List<List<Buying>>) -> Unit = {}
) {
    var emptySeasonWarning by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        ) {
            Text(
                text = "Всего потрачено: ${BuyingManager(season).total}",
                style = MaterialTheme.typography.bodyLarge
            )
            if (ended) Text(
                text = "Завершённый сезон",
                style = MaterialTheme.typography.bodyMedium
            )
            else {
                Button(
                    onClick = {
                        println("BUTTON PRESSED")
                        if (season.isEmpty()) emptySeasonWarning = true else modifyAllSeasons(newSeason())
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 2.dp
                    )
                ) {
                    Text(
                        text = "Завершить сезон",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                if (emptySeasonWarning) Text(
                    text = "Сезон пустой",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}