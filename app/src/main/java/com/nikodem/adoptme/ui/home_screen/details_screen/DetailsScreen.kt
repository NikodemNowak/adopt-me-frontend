package com.nikodem.adoptme.ui.home_screen.details_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.nikodem.adoptme.R
import com.nikodem.adoptme.ui.theme.BO50FS15
import com.nikodem.adoptme.ui.theme.mySuperFontStyle

@Composable
fun DetailsScreen(
    viewModel: DetailsScreenFragmentViewModel
) {
    val data by viewModel.viewState.observeAsState(viewModel.currentState)

    Scaffold(
        topBar = {
            DetailsTopBar(viewModel::onBackButtonClicked)
        },
        bottomBar = {
            AdoptAnimalButton(type = "Dog")
        }
    ) {
        LazyColumn {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Scrappy", style = MaterialTheme.typography.mySuperFontStyle)
                        Text(
                            text = "A beloved puppy asks for a home",
                            style = MaterialTheme.typography.BO50FS15
                        )
                    }

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_bookmark_border_24),
                            contentDescription = null
                        )
                    }
                }

            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    InfoCircle(text = "breed", R.drawable.ic_outline_info_24)
                    InfoCircle(text = "lifetime", R.drawable.ic_baseline_favorite_border_24)
                    InfoCircle(text = "color", R.drawable.ic_outline_color_lens_24)
                    InfoCircle(text = "gender", R.drawable.ic_baseline_pets_24)
                }
            }

            item {
                Row(modifier = Modifier.padding(all = 10.dp)) {
                    Text(
                        text = "askndsjdnasdnasjkdnsadnsadnaskjdnaskndsjdnasdnasjkdnsadnsadnaskjdnaskndsjdnasdnasjkdnsadnsadnaskjdnaskndsjdnasdnasjkdnsadnsadnaskjdnaskndsjdnasdnasjkdnsadnsadnaskjdnaskndsjdnasdnasjkdnsadnsadnaskjdnaskndsjdnasdnasjkdnsadnsadnaskjdn",
                        style = MaterialTheme.typography.BO50FS15
                    )
                }
            }
        }
    }
}


@Composable
fun DetailsTopBar(
    onBackArrowClicked: () -> Unit
) {
    Box {
        Image(
            painter = rememberImagePainter(
                data = "https://i.guim.co.uk/img/media/fe1e34da640c5c56ed16f76ce6f994fa9343d09d/0_174_3408_2046/master/3408.jpg?width=1200&height=900&quality=85&auto=format&fit=crop&s=0d3f33fb6aa6e0154b7713a00454c83d",
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.ic_baseline_assignment_ind_24)
                }),
            contentDescription = "",
        )
        IconButton(
            onClick = onBackArrowClicked,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 10.dp, start = 10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                contentDescription = null
            )
        }
    }
}

@Composable
fun InfoCircle(text: String, icon: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            modifier = Modifier.size(60.dp),
            elevation = 5.dp,
            shape = CircleShape
        ) {
            Icon(
                modifier = Modifier.padding(20.dp),
                painter = painterResource(id = icon),
                contentDescription = null
            )
        }
        Text(text = text, modifier = Modifier.padding(top = 10.dp))
    }
}

@Composable
fun AdoptAnimalButton(type: String) {
    Button(onClick = {}, modifier = Modifier
        .fillMaxWidth()
        .padding(all = 10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White
        )
    ) {
        Text(text = "Adopt a $type")
    }
}