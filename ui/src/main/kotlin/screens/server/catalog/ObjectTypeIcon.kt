package screens.server.catalog

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import common.components.SvgIcon
import models.catalog.ObjectType

@Composable
fun ObjectTypeIcon(
    type: ObjectType,
    size: Dp,
) {
    SvgIcon(
        svgName = when(type) {
            ObjectType.UNKNOW -> "unknow_icon"
            ObjectType.GALAXY -> "galaxy_icon"
            ObjectType.NEBULA -> "nebula_icon"
            ObjectType.STAR -> "st_icon"
            ObjectType.STAR_GROUP -> "star_group_icon"
        },
        modifier = Modifier.size(size)
    )
}