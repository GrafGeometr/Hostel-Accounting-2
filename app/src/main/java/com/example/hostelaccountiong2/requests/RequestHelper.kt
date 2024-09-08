package com.example.hostelaccountiong2.requests

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import java.net.URL


object RequestHelper {
    private val client = HttpClient(CIO)

    @OptIn(InternalAPI::class)
    fun createPostRequest(
        urlString: String,
        headers: Map<String, String>,
        jsonBody: String
    ): AppRequest = AppRequest {
        client.request(urlString) {
            method = HttpMethod.Post
            headers {
                headers.forEach {
                    append(it.key, it.value)
                }
            }
            contentType(ContentType.Application.Json)
            setBody(jsonBody)
        }
    }

    fun createGetRequest(
        urlString: String,
        headers: Map<String, String>,
        jsonBody: String = ""
    ): AppRequest = AppRequest {
        client.request(urlString) {
            method = HttpMethod.Get
            headers {
                headers.forEach {
                    append(it.key, it.value)
                }
            }
            contentType(ContentType.Application.Json)
            setBody(jsonBody)
        }
//        client.get(HttpRequestBuilder(
//            url = URL(urlString)
//        ).apply {
//            headers.forEach {
//                header(it.key, it.value)
//            }
//        }.apply {
//            setBody(jsonBody)
//        })
    }

    fun createPutRequest(
        urlString: String,
        headers: Map<String, String>,
        jsonBody: String
    ): AppRequest = AppRequest {
        client.request(urlString) {
            method = HttpMethod.Put
            headers {
                headers.forEach {
                    append(it.key, it.value)
                }
            }
            contentType(ContentType.Application.Json)
            setBody(jsonBody)
        }
    }
}