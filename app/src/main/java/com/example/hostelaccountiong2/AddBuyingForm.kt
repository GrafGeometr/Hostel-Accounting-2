package com.example.hostelaccountiong2

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hostelaccountiong2.mechanics.Buying
import com.example.hostelaccountiong2.sheets.AbstractTableManager
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbstractTableManager.AddBuyingForm(close: () -> Unit, onSubmit: (List<Buying>) -> Unit) {
    var price by remember { mutableStateOf("") }
    var notInteger by remember { mutableStateOf(false) }
    var tooBig by remember { mutableStateOf(false) }

    var description by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
            .padding(horizontal = 32.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                shape = MaterialTheme.shapes.medium
            )
            .background(
                MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = { close() },
                modifier = Modifier.height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Закрыть"
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(top = 48.dp)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // name selector dropdown

            var selectedName by remember { mutableStateOf("Кто ты?") }
            var expanded by remember { mutableStateOf(false) }
            val names = listOf("Петя", "Ярик", "Илья", "Женя", "Лёня", "Ваня").shuffled()

            Box(modifier = Modifier.fillMaxWidth()) {
                ExposedDropdownMenuBox(
                    expanded = expanded, onExpandedChange = { expanded = !expanded }, modifier = Modifier
                ) {
                    TextField(
                        value = selectedName,
                        onValueChange = { selectedName = it },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.fillMaxWidth()
                    ) {
                        names.forEach { name ->
                            DropdownMenuItem(text = { Text(text = name) }, onClick = {
                                selectedName = name
                                expanded = false
                            }, modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            // price input
            TextField(price, onValueChange = {
                price = it
                if (it.toIntOrNull() == null) {
                    notInteger = true
                    tooBig = false
                } else {
                    notInteger = false
                    tooBig = it.toInt() > 1000000
                }
            }, modifier = Modifier.fillMaxWidth(), placeholder = { Text(text = "Сколько ты потратил") })

            // price warning text
            if (notInteger) {
                Text(
                    text = "Цена должна быть целым числом!",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Red)
                )
            }
            if (tooBig) {
                Text(
                    text = "Цена должна быть меньше 1 000 000",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Red)
                )
            }

            // description input

            TextField(
                description,
                onValueChange = { description = it },
                placeholder = { Text(text = "Описание") },
                modifier = Modifier.fillMaxWidth()
            )

            // submit button
//        var showMessage by remember { mutableStateOf(false) }

            Button(
                onClick = {
//                showMessage = true
                    if (notInteger || tooBig) return@Button


                    val currentTime = Calendar.getInstance().time
                    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
                    val formattedDate = formatter.format(currentTime)

                    onSubmit(
                        addBuying(Buying(selectedName, price.toInt(), description, formattedDate))
                    )
                    price = ""
                    description = ""
                    selectedName = "Кто ты?"
                }, modifier = Modifier.fillMaxWidth().padding(16.dp).height(48.dp)
            ) {
                Text(text = "Отправить")
            }
        }
    }
}