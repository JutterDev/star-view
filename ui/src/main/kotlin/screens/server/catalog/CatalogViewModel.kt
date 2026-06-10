package screens.server.catalog

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import common.BaseViewModel
import kotlinx.coroutines.delay
import models.catalog.CatalogObject
import models.catalog.ObjectType
import screens.splash.SplashAction
import screens.splash.SplashEvent
import screens.splash.SplashState
import usecases.catalog.IGetFullCatalogUC
import usecases.serverconnection.IIsConnectionSettingsSavedUC

class CatalogViewModel(
    private val getFullCatalogUC: IGetFullCatalogUC,
): BaseViewModel<CatalogState, CatalogEvent>() {

    override fun startState() = CatalogState()

    private var searchText: String = ""
    private var catalog = listOf<CatalogObject>()
    private var selectedObject: CatalogObject? = null

    init {
        launchUI {
            withIO {
                catalog = getFullCatalogUC()
                updateList()
            }
        }
    }

    fun onAction(action: CatalogAction) {
        when(action) {
            is CatalogAction.SearchTextChange -> searchUpdate(action.text)
            is CatalogAction.CloseCatalog -> onCloseCatalog()
            is CatalogAction.SelectCatalogObject -> onSelectCatalogObject(action.obj)
            is CatalogAction.OpenFilter -> onOpenFilter()
            is CatalogAction.ChangeFilter -> onChangeFilter(action.objectType)
            is CatalogAction.OnDoneFilter -> onDoneFilter()
        }
    }

    private fun searchUpdate(text: String) {
        launchUI {
            searchText = text.toUpperCase(Locale.current)
            withIO { updateList() }
        }
    }

    private fun onCloseCatalog() {
        launchUI {
            _uiEvents.emit(CatalogEvent.Navigation.Back)
        }
    }

    private fun onSelectCatalogObject(obj: CatalogObject) {
        launchUI {
            selectedObject = obj
            _uiState.emit(uiState.value.copy(
                selectedObject = obj,
            ))
        }
    }

    private fun onOpenFilter() {
        launchUI {
            _uiState.emit(uiState.value.copy(
                filterState = uiState.value.filterState.copy(
                    isVisible = true,
                ),
            ))
        }
    }

    private fun onChangeFilter(
        objectType: ObjectType?,
    ) {
        launchUI {
            _uiState.emit(uiState.value.copy(
                filterState = uiState.value.filterState.copy(
                    objectType = objectType,
                ),
            ))
            withIO { updateList() }
        }
    }

    private fun onDoneFilter() {
        launchUI {
            _uiState.emit(uiState.value.copy(
                filterState = uiState.value.filterState.copy(
                    isVisible = false,
                ),
            ))
            withIO { updateList() }
        }
    }

    private suspend fun updateList() {
        val filteredList = catalog.filter {
            if (_uiState.value.filterState.objectType == null) return@filter true
            it.superType == _uiState.value.filterState.objectType
        }
        if (searchText.isNotBlank()) {
            _uiState.emit(uiState.value.copy(
                list = filteredList.filter {
                    it.name.contains(searchText) || it.commonNames?.toUpperCase(Locale.current)?.contains(searchText) ?: false
                },
            ))
        } else {
            _uiState.emit(uiState.value.copy(
                list = filteredList,
            ))
        }
    }
}