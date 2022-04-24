package com.nikodem.adoptme.ui.profile_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.nikodem.adoptme.BuildConfig
import com.nikodem.adoptme.R
import com.nikodem.adoptme.ui.theme.*

@Composable
fun ProfileScreen(
    viewModel: ProfileScreenFragmentViewModel
) {

    val data by viewModel.viewState.observeAsState(viewModel.currentState)
    val config = BuildConfig.VERSION_NAME

    Scaffold(
        topBar = { MyTopBar(viewModel::onBackButtonClicked) }
    ) {
        Column {
            Row(
                Modifier.padding(start = 10.dp)
            ) {
                Column {
                    Image(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(50.dp)),
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
                Column(
                    modifier = Modifier
                        .height(100.dp)
                        .padding(start = 30.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${data.firstName} ${data.lastName}",
                        style = MaterialTheme.typography.BFS20
                    )
                }
            }
            Row(
                modifier = Modifier.padding(top = 20.dp, start = 40.dp)
            ) {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_phone_24),
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
                Column(Modifier.padding(start = 30.dp)) {
                    Text(text = data.phoneNumber, style = MaterialTheme.typography.BO50FS15)
                }
            }
            Row(
                modifier = Modifier.padding(top = 20.dp, start = 40.dp)
            ) {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_alternate_email_24),
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
                Column(Modifier.padding(start = 30.dp)) {
                    Text(
                        text = data.email,
                        style = MaterialTheme.typography.BO50FS15
                    )
                }
            }
            Divider(
                color = Color.LightGray,
                thickness = 0.5.dp,
                modifier = Modifier.padding(top = 40.dp)
            )
            Row(
                Modifier
                    .padding(top = 40.dp)
                    .clickable { }
                    .fillMaxWidth()
                    .height(80.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_settings_24),
                    contentDescription = null,
                    Modifier.padding(start = 40.dp)
                )
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.BFS20,
                    modifier = Modifier.padding(start = 30.dp)
                )
            }
            Row(
                Modifier
                    .clickable {
                        viewModel.onLogoutClicked()
                    }
                    .fillMaxWidth()
                    .height(80.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_power_settings_new_24),
                    contentDescription = null,
                    Modifier.padding(start = 40.dp),
                    tint = Color.Red
                )
                Text(
                    text = "Log Out",
                    style = MaterialTheme.typography.RFS20,
                    modifier = Modifier.padding(start = 30.dp)
                )
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "AdoptMe $config", style = MaterialTheme.typography.BO50FS10)
            }
        }
    }
}

@Composable
fun MyTopBar(
    onBackArrowClicked: () -> Unit
) {

    Column(
        modifier = Modifier.padding(top = 10.dp, bottom = 25.dp, start = 10.dp, end = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = onBackArrowClicked,
                modifier = Modifier
                    .align(Alignment.Top)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = null
                )
            }
            Text(
                text = "Profile",
                modifier = Modifier
                    .align(Alignment.Top)
                    .padding(top = 6.dp, start = 30.dp),
                style = MaterialTheme.typography.BFS25BOLD
            )
        }
    }
}