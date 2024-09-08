package com.example.hostelaccountiong2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.hostelaccountiong2.mechanics.Todo
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import com.example.hostelaccountiong2.mechanics.TodoManager
import com.example.hostelaccountiong2.sheets.AbstractTableManager
import java.time.ZonedDateTime

@Composable
fun AbstractTableManager.TodoList() {
    var todoList by remember { mutableStateOf(getTodoList()) }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                .verticalScroll(rememberScrollState(Int.MAX_VALUE)), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            todoList.filter { todo ->
                // if it is done and is older than one day
                // parse date from 01.09.2024 15:29
                fun String.asDateTime(): ZonedDateTime {
                    val (dmy, hm) = this.split(" ")
                    val (day, month, year) = dmy.split(".").map { it.toInt() }
                    val (hour, minute) = hm.split(":").map { it.toInt() }
                    return ZonedDateTime.of(year, month, day, hour, minute, 0, 0, ZonedDateTime.now().zone)
                }

                !todo.done || todo.date.asDateTime().isAfter(ZonedDateTime.now().minusDays(1))
            }.forEach { todo ->
                TodoItem(todo) { todoList = it }
            }
            TodoManager.getRecommendations(todoList).also { println(it) }.filter { it.second > 0.3 }
                .let { recommendations ->
                    Divider(thickness = 1.dp)
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Мы можем посоветовать вам:",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        recommendations.forEach { suggestion ->
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = suggestion.first,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                )
                                Text(
                                    text = suggestion.second.toString().take(4).drop(2) + "%",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                )
                            }
                        }
                    }
                }
            Spacer(Modifier.height(64.dp))
        }

        Box(
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
        ) {
            Button(
                onClick = {
                    todoList = getTodoList()
//                    println(todoList)
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
            AddTodoForm(todoList, close = { showForm = false }) {
                todoList = it
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

@Composable
fun AbstractTableManager.TodoItem(todo: Todo, updateTodoList: (List<Todo>) -> Unit) {
    Box(
        modifier = Modifier.background(
            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f), shape = MaterialTheme.shapes.medium
        ).padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = todo.done, onCheckedChange = { done ->
                updateTodoList(todo.edit(done = done))
            })
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = todo.name, style = if (todo.done) {
                        MaterialTheme.typography.titleLarge.copy(textDecoration = TextDecoration.LineThrough)
                    } else {
                        MaterialTheme.typography.titleLarge
                    }
                )
                Text(
                    text = todo.description, style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = todo.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }

    }
}