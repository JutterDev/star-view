package screens.server.catalog

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import common.SingleEventEffect
import common.components.MCard
import common.components.PrimaryButton
import common.components.SvgIcon
import common.components.TextInputLine
import kotlinx.coroutines.flow.SharedFlow
import models.catalog.CatalogObject
import models.catalog.ObjectType
import models.catalog.typeFullName
import org.koin.compose.viewmodel.koinViewModel
import screens.server.ServerAction
import theme.Colors
import theme.MonitorText
import javax.management.monitor.StringMonitor

@Composable
fun CatalogScreen(
    navController: NavController,
    viewModel: CatalogViewModel = koinViewModel()
) {
    CatalogScreenContent(
        state = viewModel.uiState.collectAsStateWithLifecycle().value,
        events = viewModel.uiEvents,
        onAction = viewModel::onAction,
        onNavigation = {
            when(it) {
                is CatalogEvent.Navigation.Back -> navController.popBackStack()
            }
        }
    )
}

@Composable
fun CatalogScreenContent(
    state: CatalogState,
    events: SharedFlow<CatalogEvent>,
    onAction: (CatalogAction) -> Unit,
    onNavigation: (CatalogEvent.Navigation) -> Unit,
) {

    SingleEventEffect(events) { event ->
        when (event) {
            is CatalogEvent.Navigation -> onNavigation(event)
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .width(340.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Max),
                    ) {
                        TextInputLine(
                            placeholder = "Search object",
                            icon = "search_icon",
                            modifier = Modifier.weight(1f),
                        ) { onAction(CatalogAction.SearchTextChange(it)) }
                        MCard(
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(1.0f),
                            onClick = { onAction(CatalogAction.OpenFilter) }
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                SvgIcon(
                                    svgName = "filter_icon",
                                    modifier = Modifier.size(24.dp),
                                )
                            }
                        }
                    }
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(state.list) { obj ->
                            CatalogObjectItem(obj) { onAction(CatalogAction.SelectCatalogObject(obj)) }
                            Spacer(Modifier.height(16.dp))
                        }
                    }
                    PrimaryButton(
                        text = "Back",
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        onAction(CatalogAction.CloseCatalog)
                    }
                }

                MCard(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (!state.filterState.isVisible) {
                        if (state.selectedObject != null) {
                            CatalogObjectDetail(state.selectedObject)
                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    SvgIcon(
                                        svgName = "galaxy_icon",
                                        modifier = Modifier.size(100.dp),
                                    )
                                    Spacer(Modifier.height(16.dp))
                                    Text(
                                        text = "No selected",
                                        style = MonitorText.Regular.Sp20.White.style(),
                                    )
                                    Text(
                                        text = "Select object from NGC catalog",
                                        style = MonitorText.Regular.Sp16.Gray.style(),
                                    )
                                }
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            FilterSection(
                                state = state.filterState,
                                onSelectType = { onAction(CatalogAction.ChangeFilter(it)) },
                                onDone = { onAction(CatalogAction.OnDoneFilter) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CatalogObjectItem(obj: CatalogObject, onSelect: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onSelect()
            }
    ) {
        ObjectTypeIcon(
            type = obj.superType,
            size = 32.dp,
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = obj.name,
                style = MonitorText.Regular.Sp20.White.style(),
            )
            Text(
                text = obj.commonNames ?: "Unnamed",
                style = MonitorText.Regular.Sp16.Gray.style(),
            )
        }
        SvgIcon(
            svgName = "arrow_icon",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun CatalogObjectDetail(obj: CatalogObject) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ObjectTypeIcon(
                type = obj.superType,
                size = 100.dp,
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(
                        text = obj.name,
                        style = MonitorText.Bold.Sp32.White.style(),
                    )
                    Text(
                        text = obj.commonNames ?: "Unnamed",
                        style = MonitorText.Bold.Sp32.White.style(),
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(
                        text = obj.type,
                        style = MonitorText.Regular.Sp18.Gray.style(),
                    )
                    Text(
                        text = typeFullName(obj.type),
                        style = MonitorText.Regular.Sp18.White.style(),
                    )
                    Text(
                        text = obj.superType.name,
                        style = MonitorText.Regular.Sp18.Gray.style(),
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoBlock(
                name = "Geometry and position",
                modifier = Modifier.weight(1f)
            ) {
                Row {
                    InfoLine(
                        name = "Right Ascension",
                        value = obj.ra,
                        modifier = Modifier.weight(1f),
                    )
                    InfoLine(
                        name = "Declination",
                        value = obj.dec,
                        modifier = Modifier.weight(2f),
                    )
                }
                InfoLine(
                    name = "Constellation",
                    value = obj.constellation,
                )
                Row {
                    InfoLine(
                        name = "MajAx",
                        value = obj.majAx.toString(),
                        modifier = Modifier.weight(1f),
                    )
                    InfoLine(
                        name = "MinAx",
                        value = obj.minAx.toString(),
                        modifier = Modifier.weight(1f),
                    )
                    InfoLine(
                        name = "PosAng",
                        value = obj.posAng.toString(),
                        modifier = Modifier.weight(1f),
                    )
                }
            }
            InfoBlock(
                name = "Magnitudes",
                modifier = Modifier.weight(1f)
            ) {
                Row {
                    InfoLine(
                        name = "B-Mag",
                        value = obj.bMag.toString(),
                        modifier = Modifier.weight(1f),
                    )
                    InfoLine(
                        name = "V-Mag",
                        value = obj.vMag.toString(),
                        modifier = Modifier.weight(1f),
                    )
                    InfoLine(
                        name = "J-Mag",
                        value = obj.jMag.toString(),
                        modifier = Modifier.weight(1f),
                    )
                }
                Row {
                    InfoLine(
                        name = "H-Mag",
                        value = obj.hMag.toString(),
                        modifier = Modifier.weight(1f),
                    )
                    InfoLine(
                        name = "K-Mag",
                        value = obj.kMag.toString(),
                        modifier = Modifier.weight(2f),
                    )
                }
                InfoLine(
                    name = "surfBr",
                    value = obj.surfBr.toString(),
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoBlock(
                name = "Morphology and kinematics",
                modifier = Modifier.weight(1f)
            ) {
                InfoLine(
                    name = "Hubble",
                    value = obj.hubble ?: "-",
                )
                Row {
                    InfoLine(
                        name = "Pax",
                        value = obj.pax.toString(),
                        modifier = Modifier.weight(1f),
                    )
                    InfoLine(
                        name = "Pm-RA",
                        value = obj.pmRa.toString(),
                        modifier = Modifier.weight(1f),
                    )
                    InfoLine(
                        name = "Pm-Dec",
                        value = obj.pmDec.toString(),
                        modifier = Modifier.weight(1f),
                    )
                }
                Row {
                    InfoLine(
                        name = "RadVel",
                        value = obj.radVel.toString(),
                        modifier = Modifier.weight(1f),
                    )
                    InfoLine(
                        name = "Redshift",
                        value = obj.redshift.toString(),
                        modifier = Modifier.weight(2f),
                    )
                }
            }
            InfoBlock(
                name = "Central star",
                modifier = Modifier.weight(1f)
            ) {
                InfoLine(
                    name = "Cstar U-Mag",
                    value = obj.cstarUMag.toString(),
                )
                InfoLine(
                    name = "Cstar B-Mag",
                    value = obj.cstarVMag.toString(),
                )
                InfoLine(
                    name = "Cstar V-Mag",
                    value = obj.cstarVMag.toString(),
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoBlock(
                name = "Identifiers and directories",
                modifier = Modifier.weight(1f)
            ) {
                Row {
                    InfoLine(
                        name = "M",
                        value = obj.m.toString(),
                        modifier = Modifier.weight(1f),
                    )
                    InfoLine(
                        name = "Cstar Names",
                        value = obj.cstarNames ?: "-",
                        modifier = Modifier.weight(2f),
                    )
                }
                Row {
                    InfoLine(
                        name = "NGC",
                        value = obj.ngc.toString(),
                        modifier = Modifier.weight(1f),
                    )
                    InfoLine(
                        name = "Identifiers",
                        value = obj.identifiers ?: "-",
                        modifier = Modifier.weight(2f),
                    )
                }
                Row {
                    InfoLine(
                        name = "IC",
                        value = obj.ic.toString(),
                        modifier = Modifier.weight(1f),
                    )
                    InfoLine(
                        name = "Common names",
                        value = obj.commonNames ?: "-",
                        modifier = Modifier.weight(2f),
                    )
                }
            }
            InfoBlock(
                name = "Notes and sources",
                modifier = Modifier.weight(1f)
            ) {
                InfoLine(
                    name = "NED notes",
                    value = obj.nedNotes ?: "-",
                )
                InfoLine(
                    name = "OpenNGC notes",
                    value = obj.openNgcNotes ?: "-",
                )
                InfoLine(
                    name = "Sources",
                    value = obj.sources ?: "-",
                )
            }
        }
    }
}

@Composable
private fun InfoBlock(
    name: String,
    modifier: Modifier = Modifier,
    block: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .border(
                width = 2.dp,
                color = Colors.border,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(24.dp))
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = name,
                style = MonitorText.Bold.Sp20.White.style(),
            )
            block()
        }
    }
}

@Composable
private fun InfoLine(
    name: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = name,
            style = MonitorText.Regular.Sp16.Gray.style(),
        )
        Text(
            text = value,
            style = MonitorText.Regular.Sp18.White.style(),
            maxLines = 1,
        )
    }
}

@Composable
private fun FilterSection(
    state: FilterState,
    onSelectType: (ObjectType?) -> Unit,
    onDone: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Filter options",
            style = MonitorText.Bold.Sp24.White.style(),
        )
        Text(
            text = "Object type",
            style = MonitorText.Regular.Sp18.White.style(),
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            FilterItem(
                itemType = ObjectType.UNKNOW,
                isSelected = state.objectType == null,
                name = "ALL",
                onSelect = { onSelectType(null) }
            )
            for (item in ObjectType.entries) {
                FilterItem(
                    itemType = item,
                    isSelected = state.objectType == item,
                    name = item.name,
                    onSelect = { onSelectType(item) }
                )
            }
        }
        PrimaryButton(
            text = "Done",
            modifier = Modifier.width(290.dp),
        ) {
            onDone()
        }
    }
}

@Composable
private fun FilterItem(itemType: ObjectType, isSelected: Boolean, name: String, onSelect: () -> Unit) {
    Box(
        modifier = Modifier
            .border(
                width = 2.dp,
                color = if (isSelected) Colors.primary else Colors.border,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(24.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onSelect()
            }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            ObjectTypeIcon(
                type = itemType,
                size = 84.dp,
            )
            Text(
                text = name,
                style = MonitorText.Regular.Sp16.Gray.style(),
            )
        }
    }
}