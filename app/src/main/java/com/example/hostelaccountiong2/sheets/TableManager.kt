package com.example.hostelaccountiong2.sheets


import com.example.hostelaccountiong2.Secret
import com.example.hostelaccountiong2.mechanics.Buying
import com.example.hostelaccountiong2.mechanics.Todo
import com.example.hostelaccountiong2.mechanics.TodoList
import com.example.hostelaccountiong2.mechanics.fix
import com.example.hostelaccountiong2.requests.RequestHelper
import com.example.hostelaccountiong2.utils.JsonBuying
import com.example.hostelaccountiong2.utils.JsonList
import com.example.hostelaccountiong2.utils.JsonTodo
import com.example.hostelaccountiong2.utils.Sign.sign
import io.ktor.client.call.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.*
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object TableManager : AbstractTableManager {
    override var currentSeasonId: Int = -1
    private var currentSeasonsCount = getAllSeasons().size

    override fun newSeason(): List<List<Buying>> {
        println("CREATING NEW SEASON")
        currentSeasonId = -1
        val result = getAllSeasons() + listOf(emptyList())
        println("RESULT: $result")
        setFile(JsonList(JsonList(JsonBuying)).run { result.toJson() }, "hostel.json")
        currentSeasonsCount = result.size
        return result
    }

    override fun removeLastSeason(): List<List<Buying>> {
        if (getAllSeasons().size == 1) return getAllSeasons()
        currentSeasonId = -1
        val result = getAllSeasons().dropLast(1)
        setFile(JsonList(JsonList(JsonBuying)).run { result.toJson() }, "hostel.json")
        currentSeasonsCount = result.size
        return result
    }

    fun setFile(content: String, filename: String) {
        val date = ZonedDateTime.now()
        val rfc1123Format = DateTimeFormatter.RFC_1123_DATE_TIME
        val dateValue = date.format(rfc1123Format)
        println(dateValue)
        val stringToSign = "PUT\n\n$Secret.contentType\n$dateValue\n/$Secret.bucketName/$filename"
        val signature = sign(stringToSign, Secret.awsSecretKey)

        runBlocking {
            RequestHelper.createPutRequest(
                "https://storage.yandexcloud.net/${Secret.bucketName}/$filename", headers = mapOf(
                    "Host" to "storage.yandexcloud.net",
                    "Date" to dateValue,
                    "Content-Type" to Secret.contentType,
                    "Authorization" to "AWS ${Secret.awsKeyId}:$signature"
                ), jsonBody = content
            ).send().body<String>()
        }
    }

    override fun addBuying(buying: Buying): List<Buying> {
        if (currentSeasonId != -1) return getBuyingList()

        var seasons = getAllSeasons()
        seasons = seasons.dropLast(1) + listOf(seasons.last() + buying)

        setFile(JsonList(JsonList(JsonBuying)).run { seasons.toJson() }, "hostel.json")

        return seasons.last()
    }

    override fun removeBuying(buying: Buying): List<Buying> {
        if (currentSeasonId != -1) return getBuyingList()

        var seasons = getAllSeasons()
        seasons = seasons.dropLast(1) + listOf(seasons.last() - buying)

        setFile(JsonList(JsonList(JsonBuying)).run { seasons.toJson() }, "hostel.json")

        return seasons.last()
    }

    private fun getJson(filename: String) = runBlocking {
        RequestHelper.createGetRequest(
            urlString = "https://hostel-accouting-backet.website.yandexcloud.net/$filename",
            headers = emptyMap(),
            jsonBody = ""
        ).send().body<String>()
    }

    override fun getAllSeasons() = getJson("hostel.json").let { text ->
        Json.decodeFromString<JsonArray>(text).jsonArray.map { seasonArray ->
            seasonArray.jsonArray.map { element ->
                element.jsonArray.toList().let {
                    Buying(
                        it[0].jsonPrimitive.content,
                        it[1].jsonPrimitive.content.toInt(),
                        it[2].jsonPrimitive.content,
                        it[3].jsonPrimitive.content
                    )
                }
            }
        }
    }.also { println(it) }

    override fun getTodoList(): TodoList = getJson("shopping.json").let { text ->
        Json.decodeFromString<JsonArray>(text).jsonArray.map { todo ->
            todo.jsonArray.toList().let {
                println(it)
                Todo(
                    it[0].jsonPrimitive.content,
                    it[1].jsonPrimitive.content,
                    it[2].jsonPrimitive.content,
                    it[3].jsonPrimitive.content.toBoolean()
                )
            }
        }
    }.fix()

    override fun addTodo(todo: Todo): TodoList {
        val result = (getTodoList() + listOf(todo)).fix()

        setFile(JsonList(JsonTodo).run { result.toJson() }, "shopping.json")

        return result.fix()
    }

    override fun Todo.edit(name: String, description: String, date: String, done: Boolean): TodoList {
        val result = (getTodoList() - this + Todo(name, description, date, done)).fix()

        setFile(JsonList(JsonTodo).run { result.toJson() }, "shopping.json")

        return result
    }


    override fun getBuyingList(): List<Buying> = getAllSeasons().let { seasons ->
        if (currentSeasonsCount != seasons.size) {
            currentSeasonsCount = seasons.size
            currentSeasonId = -1
        }
        if (currentSeasonId == -1) seasons.last() else seasons[currentSeasonId]
    }
}