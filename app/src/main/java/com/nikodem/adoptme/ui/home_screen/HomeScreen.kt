package com.nikodem.adoptme.ui.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import coil.compose.rememberImagePainter
import com.nikodem.adoptme.R
import com.nikodem.adoptme.ui.theme.*

@Composable
fun HomeScreen(
    viewModel: HomeScreenFragmentViewModel
) {
    val data by viewModel.viewState.observeAsState(viewModel.currentState)

    Scaffold(
        topBar = {
            MyTopBar()
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (i in 0 until 20) {
                item {
                    AnimalListItem()
                }
            }

//            item {
//                Text(text = "hej")
//                Text(text = "hej2")
//                Button(onClick = viewModel::onButtonClick) {
//                    Text(text = "Loading: ${data.isLoading}")
//                }
//            }
//
//            item {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 20.dp)
//                        .background(Color.Blue)
//                        .padding(top = 10.dp),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ){
//                    Text(text = "hej")
//                    Text(text = "hej2")
//                    Text(text = "3")
//                }
//            }

        }
    }
}

@Composable
fun AnimalListItem() {
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
                data = "https://i.guim.co.uk/img/media/fe1e34da640c5c56ed16f76ce6f994fa9343d09d/0_174_3408_2046/master/3408.jpg?width=1200&height=900&quality=85&auto=format&fit=crop&s=0d3f33fb6aa6e0154b7713a00454c83d",
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
                text = "Scrappy",
                style = MaterialTheme.typography.mySuperFontStyle
            )

            Text(
                text = "aksdma sljdnaj kdnajsdnjan sdjasdnkas ndjdsad aksdma sljdnaj kdnajsdnjan sdjasdnkas ndjdsad",
                style = MaterialTheme.typography.mySecondFontStyle

            )
        }
    }
}

@Composable
fun MyTopBar() {
    var animalType by remember { mutableStateOf(AnimalType.ALL) }

    Column(
        modifier = Modifier.padding(top = 50.dp, bottom = 25.dp, start = 20.dp, end = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Shelter", style = MaterialTheme.typography.myTitleFontStyle)
                Text(
                    text = "Give a house to one of our charges.",
                    style = MaterialTheme.typography.mySecondFontStyle
                )
            }

            Image(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(20.dp)),
                painter = rememberImagePainter(
                    data = "https://i0.wp.com/post.medicalnewstoday.com/wp-content/uploads/sites/3/2020/03/GettyImages-1092658864_hero-1024x575.jpg?w=1155&h=1528",
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

enum class AnimalType(val value: String){
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