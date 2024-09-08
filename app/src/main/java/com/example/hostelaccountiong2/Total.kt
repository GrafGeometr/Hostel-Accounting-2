package com.example.hostelaccountiong2

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.hostelaccountiong2.mechanics.BuyingManager
import com.example.hostelaccountiong2.mechanics.Transaction
import com.example.hostelaccountiong2.sheets.AbstractTableManager
import kotlin.math.roundToInt

@Composable
fun AbstractTableManager.Total() {
    val buyingManager = BuyingManager(getBuyingList())

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).verticalScroll(rememberScrollState()).background(
            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f), shape = MaterialTheme.shapes.medium
        ), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DisplayToPay(buyingManager.resultingTransactions.shuffled())
        Text(
            text = "Статистика",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        CommonStatistics(buyingManager.total, buyingManager.average)
        PersonStatistic(buyingManager.personsTotalBuying.toList().sortedBy { it.second }.reversed())
    }
}

@Composable
private fun PersonStatistic(personTotalBuying: List<Pair<String, Int>>) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        personTotalBuying.forEach { (name, amount) ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = name, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = amount.toString(),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}

@Composable
private fun CommonStatistics(total: Int, average: Double) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Всего потрачено:"
                )
                Text(
                    text = total.toString(),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "В среднем на человека:"
                )
                Text(
                    text = average.roundToInt().toString(),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
private fun DisplayToPay(toPay: List<Transaction>) {
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "Нужно перевести",
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        toPay.forEach {
            val (payer, amount, earner) = it
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        payer, style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "-$amount",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                    )
                }

                Column(
                    modifier = Modifier.width(128.dp).clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.primaryContainer).padding(8.dp).border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            shape = MaterialTheme.shapes.medium
                        )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.defaultMinSize(minWidth = 72.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                amount.toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,

                                )
                        }
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null
                        )
                    }
                }

                Column(
                    modifier = Modifier.clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        earner,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        text = "+$amount",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}