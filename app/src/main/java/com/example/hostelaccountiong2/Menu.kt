package com.example.hostelaccountiong2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hostelaccountiong2.sheets.AbstractTableManager

@Composable
fun AbstractTableManager.Menu(modifier: Modifier = Modifier) {
    var showTotal by remember { mutableStateOf(false) }
    var showBuyingList by remember { mutableStateOf(true) }
    var showSettings by remember { mutableStateOf(false) }
    var showTodoList by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Button(
                    onClick = {
                        showTotal = false
                        showBuyingList = true
                        showSettings = false
                        showTodoList = false
                    }, modifier = Modifier.padding(start = 8.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = if (showBuyingList) MaterialTheme.colorScheme.background
                        else MaterialTheme.colorScheme.primary,
                        contentColor = if (showBuyingList) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onPrimary
                    )
                ) { Text(text = "Покупки") }
                Button(
                    onClick = {
                        showTotal = true
                        showBuyingList = false
                        showSettings = false
                        showTodoList = false
                    }, modifier = Modifier.padding(horizontal = 8.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = if (showTotal) MaterialTheme.colorScheme.background
                        else MaterialTheme.colorScheme.primary,
                        contentColor = if (showTotal) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onPrimary
                    )
                ) { Text(text = "Итог") }
//                Button(
//                    onClick = {
//                        showTotal = false
//                        showBuyingList = false
//                        showSettings = true
//                        showTodoList = false
//                    }, modifier = Modifier.padding(horizontal = 8.dp), colors = ButtonDefaults.buttonColors(
//                        containerColor = if (showSettings) MaterialTheme.colorScheme.background
//                        else MaterialTheme.colorScheme.primary,
//                        contentColor = if (showSettings) MaterialTheme.colorScheme.primary
//                        else MaterialTheme.colorScheme.onPrimary
//                    )
//                ) { Text(text = "Настройки") }
                Button(
                    onClick = {
                        showTotal = false
                        showBuyingList = false
                        showSettings = false
                        showTodoList = true
                    }, modifier = Modifier.padding(end = 8.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = if (showTodoList) MaterialTheme.colorScheme.background
                        else MaterialTheme.colorScheme.primary,
                        contentColor = if (showTodoList) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onPrimary
                    )
                ) { Text(text = "Список дел") }
            }
        }
        if (showTotal) {
            Total()
        }
        if (showBuyingList) {
            BuyingList()
        }
//        if (showSettings) {
//            Settings()
//        }
        if (showTodoList) {
            TodoList()
        }
    }
}

