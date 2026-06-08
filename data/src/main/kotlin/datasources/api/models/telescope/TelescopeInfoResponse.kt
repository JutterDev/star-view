package datasources.api.models.telescope

import kotlinx.serialization.Serializable
import models.telescope.TelescopeInfo

@Serializable
data class TelescopeInfoResponse(
    val name: String,
    val lat: Float,
    val lon: Float,
)

class TelescopeInfoMapper {

    fun toDomain(response: List<TelescopeInfoResponse>) = response.map { item ->
        TelescopeInfo(
            name = item.name,
            lat = item.lat,
            lon = item.lon,
        )
    }
}