package datasources.api

import datacontracts.SettingsRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.logging.*

class Service(
    val settingsRepository: SettingsRepository,
) {

    val client = HttpClient(CIO) {

        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.BODY
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                encodeDefaults = true
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 15000  // Время на весь запрос (15 сек)
            connectTimeoutMillis = 10000  // Время на подключение (10 сек)
            socketTimeoutMillis = 10000   // Время на ожидание данных (10 сек)
        }
    }

    val baseUrl
        get() = "http://${settingsRepository.ip}:${settingsRepository.port}/api/1.0"

    suspend inline fun <reified RES> get(url: String, query: HashMap<String, String> = hashMapOf()): RES {
        val response = client.get("$baseUrl/$url") {
            contentType(ContentType.Application.Json)
            query.forEach {
                parameter(it.key, it.value)
            }

            header(HttpHeaders.Accept, "application/json")
        }.body<DataWrapper<RES>>()
        if (!response.success) error(response.message ?: "Network error(")
        return response.data!!
    }

    suspend inline fun <reified REQ, reified RES> post(url: String, body: REQ): RES {
        val response = client.post("$baseUrl/$url") {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Accept, "application/json")
            setBody(body)
        }
        val wrapper = response.body<DataWrapper<RES>>()
        if (!wrapper.success) error(wrapper.message ?: "Network error(")
        return wrapper.data!!
    }
}

@Serializable
data class DataWrapper<T> (
    var success: Boolean = false,
    var message: String? = null,
    var data: T? = null
)