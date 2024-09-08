package com.example.hostelaccountiong2.mechanics

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.E
import kotlin.math.pow

object TodoManager {
    fun findAutocomplete(text: String, todoList: TodoList): List<String> = if (text.isEmpty()) emptyList() else {
        todoList.map { it.name.lowercase() }.distinct().filter {
            it.length >= text.length && it.contains(text.lowercase())
        }
    }

    fun getProbability(days: Int, previousResults: List<Int>): Double {
        // calculate probability that number is time if previousResults

        val lambda =
            if (previousResults.size < 2) days + 2.0 else previousResults.sum() / previousResults.size.toDouble()

        fun p(x: Int): Double {
            var res = E.pow(-lambda)
            (1..x).forEach {
                res *= lambda / it.toDouble()
            }
            return res
        }

//        println(p(days))

//        (0..<days).forEach { println(p(it)) }
        return (0..days).sumOf { p(it) }
//        return (p(days) / (1.0 - (0..<days).sumOf { p(it) }))

    }

    fun getRecommendations(todoList: TodoList): List<Pair<String, Double>> {
        val goods = todoList.filter { it.done }.groupBy { it.name.lowercase() }.map { (name, todo) ->
            name to todo.map { dt ->
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                LocalDate.parse(dt.date, formatter).toEpochDay().toInt()
            }
        }
        println(goods)
        val differences = goods.map { (name, dates) ->
            // find periods between dates
            name to (0..dates.size - 2).map { dates[it + 1] - dates[it] }.toList()
        }
        println(differences)
        val currentEpochDay = LocalDate.now().toEpochDay().toInt()
        println(currentEpochDay)
        val result = goods.zip(differences).map {
            Triple(it.first.first, it.first.second.last(), it.second.second)
        }.also { println(it) }.map {
            it.first to getProbability(currentEpochDay - it.second, it.third)
        }.sortedBy { 1 - it.second }
        return result.take(5)
            .filter { !todoList.filter { todo -> !todo.done }.map { todo -> todo.name }.contains(it.first) }
    }
}


