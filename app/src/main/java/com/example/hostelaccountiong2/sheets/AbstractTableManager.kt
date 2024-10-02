package com.example.hostelaccountiong2.sheets

import com.example.hostelaccountiong2.mechanics.Buying
import com.example.hostelaccountiong2.mechanics.Todo
import com.example.hostelaccountiong2.mechanics.TodoList

interface AbstractTableManager {
    var currentSeasonId: Int

    fun newSeason(): List<List<Buying>>

    fun removeLastSeason(): List<List<Buying>>

    fun addBuying(buying: Buying): List<Buying>

    fun removeBuying(buying: Buying): List<Buying>

    fun getBuyingList(): List<Buying>

    fun getAllSeasons(): List<List<Buying>>

    fun getTodoList(): TodoList

    fun addTodo(todo: Todo): TodoList

    fun removeTodo(todo: Todo): TodoList

    fun Todo.edit(
        name: String = this.name,
        description: String = this.description,
        date: String = this.date,
        done: Boolean = this.done
    ): TodoList
}