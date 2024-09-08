package com.example.hostelaccountiong2.mechanics

import kotlin.math.roundToInt

class BuyingManager(buyingList: List<Buying>) {
    val total: Int = buyingList.sumOf { it.price }
    val personsTotalBuying: Map<String, Int>
    val resultingTransactions: MutableList<Transaction>
    val average: Double

    init {

        val defaultPersons = listOf("Петя", "Женя", "Ваня", "Ярик", "Лёня", "Илья")

        personsTotalBuying =
            buyingList.groupBy { it.name }.mapValues { list -> list.value.sumOf { it.price } }.toMutableMap()

        defaultPersons.forEach {
            if (!personsTotalBuying.containsKey(it)) personsTotalBuying[it] = 0
        }

        val arr: MutableList<Pair<Double, String>> = mutableListOf()


        resultingTransactions = mutableListOf()

        val mp = personsTotalBuying.toMutableMap()

        average = mp.values.sum() / 6.0
        for ((name, value) in mp) {
            arr.add(Pair(value - average, name))
        }

        while (arr.size > 1) {
            arr.sortBy { it.first }
            val p1 = arr.removeAt(0)
            val p2 = arr.removeAt(arr.size - 1)
//            println("${p1.second} -> ${p2.second} ${-p1.first}")
            resultingTransactions += Transaction(p1.second, (-p1.first).roundToInt(), p2.second)
            arr.add(Pair(p1.first + p2.first, p2.second))
        }
//        Triple(resultingTransactions, persons, listOf(total, avg.toInt()))
    }

}