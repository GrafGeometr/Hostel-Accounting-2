package com.example.hostelaccountiong2.requests

import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppRequest(val request: suspend () -> HttpResponse) {
    suspend fun send(): HttpResponse {
        return request().also {
            println(it.status)
            println(it.bodyAsText())
            println()
        }
    }

    fun runInParallel() {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            send()
        }
    }
}