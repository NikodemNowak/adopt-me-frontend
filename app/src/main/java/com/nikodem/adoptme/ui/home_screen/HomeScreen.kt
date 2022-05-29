package com.nikodem.adoptme.ui.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nikodem.adoptme.R
import com.nikodem.adoptme.db.entity.AnimalDB
import com.nikodem.adoptme.ui.theme.*
import com.nikodem.adoptme.utils.nullableString
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    viewModel: HomeScreenFragmentViewModel
) {
    val data by viewModel.viewState.observeAsState(viewModel.currentState)
    val animals = viewModel.animals.collectAsLazyPagingItems()
    var isRefreshing by remember { mutableStateOf(false) }

    val lazyListState = rememberLazyListState()
    val loadState = animals.loadState

    LaunchedEffect(Unit) {
        snapshotFlow { animals.loadState }.collectLatest { loadState ->
            if (loadState.refresh !is LoadState.Loading && loadState.source.refresh !is LoadState.Loading) {
                isRefreshing = false
            }
        }
    }

    Scaffold(
        topBar = {
            MyTopBar(viewModel::navigateToProfile, data.profilePicture)
        }
    ) { paddingValues ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = {
                animals.refresh()
                isRefreshing = true
            }
        ) {
            if (loadState.refresh is LoadState.Loading || loadState.source.refresh is LoadState.Loading) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 20.dp),
                        text = "Loading..."
                    )
                }
            } else if (animals.loadState.mediator?.refresh is LoadState.Error) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "Cos poszlo nie tak")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    state = lazyListState
                ) {
                    items(animals) { animal ->
                        animal?.let {
                            AnimalListItem(
                                animal,
                                viewModel::onItemClicked
                            )
                        }
                    }

                    if (loadState.append is LoadState.Error) {
                        item {
                            Text(text = "blad ladowania")
                            Button(onClick = {}) {
                                Text(text = "Retry")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnimalListItem(
    animal: AnimalDB,
    onClicked: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

//    val name = "Adam" - niepoprawne
//    var name by remember { mutableStateOf("Adam") } - poprawne

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(bounded = true),
                onClick = {
                    onClicked.invoke(animal.animalId)
                }
            )
            .padding(bottom = 20.dp, top = 20.dp)
    ) {

        Image(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .size(90.dp)
                .clip(RoundedCornerShape(20.dp)),
            painter = rememberImagePainter(
                data = animal.photo,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.ic_baseline_assignment_ind_24)
                }
            ),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        Column {
            Text(
                text = animal.name.nullableString(),
                style = MaterialTheme.typography.mySuperFontStyle
            )

            Text(
                text = animal.description.nullableString(),
                style = MaterialTheme.typography.BO50FS15,
            )
        }
    }
}

@Composable
fun MyTopBar(navigateToProfile: () -> Unit, profilePicture: String) {
    var animalType by remember { mutableStateOf(AnimalType.ALL) }

    Column(
        modifier = Modifier.padding(top = 50.dp, bottom = 25.dp, start = 20.dp, end = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Shelter", style = MaterialTheme.typography.BFS25BOLD)
                Text(
                    text = "Give a house to one of our charges.",
                    style = MaterialTheme.typography.BO50FS15
                )
            }

            Image(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable(
                        enabled = true,
                        onClickLabel = "Profile",
                        onClick = navigateToProfile
                    ),
                painter = rememberImagePainter(
                    data = profilePicture,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.ic_baseline_assignment_ind_24)
                    }
                ),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }

        Row {
            ChipGroup(
                selectedType = animalType,
                onSelectedChanged = {
                    animalType = it
                }
            )
        }
    }
}

@Composable
fun Chip(
    name: String = "Chip",
    isSelected: Boolean = false,
    onSelectionChanged: (String) -> Unit = {},
) {
    Surface(
        modifier = Modifier.padding(4.dp),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) Color.Blue else MaterialTheme.colors.primary
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectionChanged(name)
                }
            )
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.body2,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ChipGroup(
    types: List<AnimalType> = AnimalType.values().toList(),
    selectedType: AnimalType,
    onSelectedChanged: (AnimalType) -> Unit = {},
) {
    Column(modifier = Modifier.padding(8.dp)) {
        LazyRow {
            items(types) {
                Chip(
                    name = it.value,
                    isSelected = selectedType == it,
                    onSelectionChanged = { str ->
                        onSelectedChanged(it)
                    },
                )
            }
        }
    }
}

enum class AnimalType(val value: String) {
    ALL("All animals"),
    DOGS("Dogs"),
    CATS("Cats"),
}

//fun getCar(value: String): Car? {
//    val map = Car.values().associateBy(Car::value)
//    return map[value]
//}

//
//@Preview
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen()
//}