package com.example.hostelaccountiong2.mechanics

import java.time.ZonedDateTime
import kotlin.reflect.typeOf

data class Todo(val name: String, val description: String, val date: String, val done: Boolean)

typealias TodoList = List<Todo>


fun TodoList.fix() = distinctBy { listOf(it.name, it.description, it.date) }.sortedBy { it.date }.sortedBy { !it.done }