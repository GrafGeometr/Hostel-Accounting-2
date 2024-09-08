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
import androidx.compose.ui.unit.dp
import com.example.hostelaccountiong2.mechanics.Todo
import com.example.hostelaccountiong2.mechanics.TodoList
import com.example.hostelaccountiong2.mechanics.TodoManager
import com.example.hostelaccountiong2.sheets.AbstractTableManager
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbstractTableManager.AddTodoForm(todoList: TodoList, close: () -> Unit, onSubmit: (List<Todo>) -> Unit) {
    var name by remember { mutableStateOf("") }

    var description by remember { mutableStateOf("") }
    Box(
        modifier = Modifier.fillMaxWidth().padding(top = 32.dp).padding(horizontal = 32.dp).border(
            width = 2.dp,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
            shape = MaterialTheme.shapes.medium
        ).background(
            MaterialTheme.colorScheme.background, shape = MaterialTheme.shapes.medium
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(end = 16.dp), horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = { close() }, modifier = Modifier.height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close, contentDescription = "Закрыть"
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp).padding(top = 48.dp).padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // price input

            var suggestions by remember { mutableStateOf(TodoManager.findAutocomplete(name, todoList)) }
            var expanded by remember { mutableStateOf(suggestions.isNotEmpty()) }


            TextField(
                value = name,
                onValueChange = {
                    name = it
                    suggestions = TodoManager.findAutocomplete(name, todoList)
                    expanded = suggestions.isNotEmpty()
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Что сделать?") },
            )

            if (expanded) Column(
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.background, shape = MaterialTheme.shapes.medium
                )
            ) {
                suggestions.forEach { suggestion ->
                    Surface(modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f),
                        shape = MaterialTheme.shapes.medium,
                        onClick = {
                            name = suggestion
                            expanded = false
                        }) {
                        Text(
                            text = suggestion,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
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
                    val currentTime = Calendar.getInstance().time
                    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
                    val formattedDate = formatter.format(currentTime)

                    onSubmit(
                        addTodo(Todo(name.lowercase().trim(), description, formattedDate, false))
                    )
                    name = ""
                    description = ""
                }, modifier = Modifier.fillMaxWidth().padding(16.dp).height(48.dp)
            ) {
                Text(text = "Отправить")
            }
        }
    }
}
