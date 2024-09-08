package com.example.hostelaccountiong2.utils

import com.example.hostelaccountiong2.mechanics.Buying
import com.example.hostelaccountiong2.mechanics.Todo

sealed interface JsonAble<T> {
    fun T.toJson(): String
}

data object JsonBuying : JsonAble<Buying> {
    override fun Buying.toJson(): String =
        """["${this.name}", "${this.price}", "${this.description}", "${this.date}"]"""
}

data object JsonTodo : JsonAble<Todo> {
    override fun Todo.toJson(): String = """["${this.name}", "${this.description}", "${this.date}", "${this.done}"]"""
}

data class JsonList<T>(val jsonMaker: JsonAble<T>) : JsonAble<List<T>> {
    override fun List<T>.toJson(): String = "[" + joinToString(separator = ",") {
        jsonMaker.run { it.toJson() }
    } + "]"
}