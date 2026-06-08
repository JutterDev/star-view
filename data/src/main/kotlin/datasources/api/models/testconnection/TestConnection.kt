package datasources.api.models.testconnection

import kotlinx.serialization.Serializable
import models.TestConnectionResult

@Serializable
class TestConnectionRequest(
    val key: String,
    val version: String = "1.0",
)

@Serializable
class TestConnectionResponse(
    val pointName: String,
)

class TestConnectionMapper {

    fun toDomain(response: TestConnectionResponse) = TestConnectionResult(
        pointName = response.pointName,
    )
}