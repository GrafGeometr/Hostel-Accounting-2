package com.example.hostelaccountiong2


import com.example.hostelaccountiong2.mechanics.Todo
import com.example.hostelaccountiong2.mechanics.TodoManager
import com.example.hostelaccountiong2.sheets.TableManager
import com.example.hostelaccountiong2.utils.JsonBuying
import com.example.hostelaccountiong2.utils.JsonList
import com.example.hostelaccountiong2.utils.JsonTodo
import kotlin.math.pow


fun main() {
//    TableManager.setFile("[]", "shopping.json")
//    val lambda = 2.0
//    for (k in 0..10) {
//        println(lambda.pow(k) / fact(k) * 2.71.pow(-lambda))
//    }

//    val todoList = listOf(
//        Todo("Уборка", "Убери мусор", "27.08.2024 10:00", true),
//        Todo("Купить хлеб", "Купи хлеба", "28.08.2024 11:00", true),
//        Todo("Купить молоко", "Купи молока", "29.08.2024 12:00", true),
//        Todo("Купить хлеб", "Купи хлеба", "30.08.2024 13:00", true),
//        Todo("Купить молоко", "Купи молока", "31.08.2024 14:00", true),
//        Todo("Купить хлеб", "Купи хлеба", "01.09.2024 15:00", true),
//    )
//    println(TodoManager.getRecommendations(todoList))

//    (0..30).forEach {
//        println(
//            TodoManager.getProbability(it, listOf(7, 7))
//        )
//        println()
//    }
//    TableManager.run {
//        setFile(JsonList(JsonTodo).run {
//            getTodoList().filter {
//                it.name.lowercase().none { char -> (('0'..'9') + ('a'..'z')).contains(char) }
//            }.toJson()
//        }, "shopping.json")
//    }
    TableManager.run {
        setFile(JsonList(JsonTodo).run {
            getTodoList().map {
                it.copy(name = it.name.lowercase().trim())
            }.toJson()
        }, "shopping.json")
    }
}



