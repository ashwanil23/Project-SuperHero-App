package com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.R
import kotlinx.coroutines.launch

@Composable
fun ModelNavigationDrawer(
    modifier: Modifier = Modifier,
    isMenuClicked: Boolean,
    onDrawerClose: () -> Unit,
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(isMenuClicked) {
        scope.launch {
            if (isMenuClicked) {
                drawerState.open()
            } else {
                drawerState.close()
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Menu",
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(R.dimen.medium)),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.image5),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.medium))
                            .size(dimensionResource(R.dimen.xlarge))
                            .clip(shape = CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.inverseSurface
                )
                Spacer(modifier = Modifier.padding(vertical = 1.dp))
                LazyColumn{
                    items((1..20).toList()){counter ->
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                "$counter.",
                                modifier = Modifier
                                    .padding(dimensionResource(R.dimen.medium))
                            )
                            NavigationDrawerItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = dimensionResource(R.dimen.medium)),
                                label = { Text(text = "Drawer Item") },
                                selected = false,
                                onClick = { /* Handle item click */ }
                            )
                        }
                    }
                }

                // Additional drawer items...
            }
        },
        gesturesEnabled = true,
        content = content,

    )
    LaunchedEffect(drawerState.currentValue) {
        if (drawerState.currentValue == DrawerValue.Closed) {
            onDrawerClose()
        }
    }
}
