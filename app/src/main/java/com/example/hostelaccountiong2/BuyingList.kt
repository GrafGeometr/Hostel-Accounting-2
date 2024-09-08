package com.example.hostelaccountiong2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hostelaccountiong2.sheets.AbstractTableManager

@Composable
fun AbstractTableManager.BuyingList() {
    var data by remember { mutableStateOf(getBuyingList()) }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                .verticalScroll(rememberScrollState(Int.MAX_VALUE)), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            data.forEach { buying ->
                // display transactions like messages in a chat
                Box(
                    modifier = Modifier.background(
                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f),
                        shape = MaterialTheme.shapes.medium
                    ).padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = buying.name, style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "${buying.price} - ${buying.description}",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Text(
                                text = buying.date,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        }
                        IconButton(onClick = {
                            data = removeBuying(buying)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Удалить",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
            if (currentSeasonId == -1) Spacer(Modifier.height(64.dp))
        }
        if (currentSeasonId == -1) {
            Box(
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
            ) {
                Button(
                    onClick = {
                        data = getBuyingList()
                    }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Обновить",
                        tint = MaterialTheme.colorScheme.onError
                    )
                }
            }
            var showForm by remember { mutableStateOf(false) }
            if (showForm) {
                AddBuyingForm(close = { showForm = false }) {
                    data = it
                    showForm = false
                }
            }
            Box(
                modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
            ) {
                Button(
                    onClick = {
                        showForm = true
                    }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Добавить покупку",
                        tint = MaterialTheme.colorScheme.onError
                    )
                }
            }
        }
    }
}

