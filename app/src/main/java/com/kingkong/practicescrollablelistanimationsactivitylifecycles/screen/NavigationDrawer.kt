package com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextButton
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.R
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.Routes
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.enumClass.UserRole
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.viewmodel.HomeScreenViewModel
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.viewmodel.NavigationItemViewModel
import kotlinx.coroutines.launch


@Composable
fun ModelNavigationDrawer(
    navController: NavHostController = rememberNavController(),
    isMenuClicked: Boolean,
    onDrawerClose: () -> Unit,
    homeListScreenViewModel: HomeScreenViewModel = viewModel(),
    navigationItemViewModel: NavigationItemViewModel = viewModel(),
    content: @Composable () -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val homeScreenUiState by homeListScreenViewModel.uiState.collectAsState()
    val navigationItemUiState by navigationItemViewModel.uiState.collectAsState()

    LaunchedEffect(isMenuClicked) {
        scope.launch {
            if (isMenuClicked) {
                drawerState.open()
            } else {
                drawerState.close()
            }
        }
    }

    LaunchedEffect(Unit) {
        navigationItemViewModel.loadItems(UserRole.REGULAR_USER)
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
                    modifier = Modifier.weight(1f),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.inverseSurface
                )
                Spacer(modifier = Modifier.padding(vertical = 1.dp))
                LazyColumn(
                    modifier = Modifier
                ) {
                    item{
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .padding(dimensionResource(R.dimen.medium))
                        ) {
                            LazyHorizontalGrid(
                                rows = GridCells.Fixed(1),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(8) { index ->
                                    Box(
                                        modifier = Modifier
                                            .size(100.dp)
                                            .weight(2f)
                                            .padding(dimensionResource(R.dimen.small))
                                            .background(MaterialTheme.colorScheme.primary)
                                    ) {
                                        Text(
                                            text = "Box ${index + 1}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                    }

                                }
                            }
                        }
                    }

                    item{
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(440.dp)
                                .padding(dimensionResource(R.dimen.medium))
                        ) {
                            LazyColumn {
                                items(navigationItemUiState.items) { item ->
                                    Spacer(Modifier.padding(dimensionResource(R.dimen.xsmall)))
                                    NavigationDrawerItem(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = dimensionResource(R.dimen.small)),
                                        label = { Text(text = stringResource(id = item.titleRes)) },
                                        icon = {
                                            Icon(
                                                painter = painterResource(id = item.iconRes),
                                                contentDescription = null
                                            )
                                        },
                                        selected = false,
                                        onClick = {
                                            navController.navigate(route = item.route)
                                            scope.launch { drawerState.close() }
                                        },
                                        colors = NavigationDrawerItemDefaults
                                            .colors(
                                                unselectedContainerColor = Color.Transparent,
                                                selectedContainerColor = Color.Transparent,
                                                unselectedIconColor = LocalContentColor.current,
                                                selectedIconColor = LocalContentColor.current,
                                                unselectedTextColor = LocalContentColor.current,
                                                selectedTextColor = LocalContentColor.current
                                            )
                                    )
                                    Spacer(Modifier.padding(dimensionResource(R.dimen.xsmall)))
                                }
                            }
                        }
                    }

                    item{
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            TextButton(
                                onClick = {
                                    scope.launch{
                                        drawerState.close()
                                        navController.navigate(Routes.TERMS_AND_CONDITIONS)
                                    }
                                }
                            ) {
                                Text(
                                    text = "Terms & Conditions",
                                    modifier = Modifier,
                                    color = Color.Blue
                                )
                            }
                        }
                    }
                }

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






