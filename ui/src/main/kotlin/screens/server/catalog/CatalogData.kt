package screens.server.catalog

import models.catalog.CatalogObject
import models.catalog.ObjectType

data class CatalogState(
    val list: List<CatalogObject> = emptyList(),
    val selectedObject: CatalogObject? = null,
    val filterState: FilterState = FilterState(),
)

data class FilterState(
    val isVisible: Boolean = false,
    val objectType: ObjectType? = null,
)

sealed class CatalogAction {

    data class SearchTextChange(val text: String): CatalogAction()

    data object CloseCatalog: CatalogAction()

    data class SelectCatalogObject(val obj: CatalogObject): CatalogAction()

    data object OpenFilter: CatalogAction()

    data class ChangeFilter(
        val objectType: ObjectType,
    ): CatalogAction()
}

sealed class CatalogEvent {

    sealed class Navigation: CatalogEvent() {

        data object Back: Navigation()
    }
}