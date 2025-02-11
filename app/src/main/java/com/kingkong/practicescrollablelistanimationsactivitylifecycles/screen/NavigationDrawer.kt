package com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen

//noinspection UsingMaterialAndMaterial3Libraries
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.system.exitProcess


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
                MenuHeader()
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
                        ImageGrid()
                    }

                    item{
                        NavigationItemList(navigationItemViewModel = navigationItemViewModel,
                            navController = navController,
                            scope,
                            drawerState
                        )
                    }

                    item{
                        Footer(navController = navController,
                            scope = scope,
                            drawerState = drawerState
                        )
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

@Composable
fun Footer(
    navController: NavHostController = rememberNavController(),
    scope: CoroutineScope,
    drawerState: DrawerState
) {
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
                text = stringResource(R.string.Terms_Conditions_Title),
                modifier = Modifier,
                color = Color.Blue
            )
        }

        Spacer(Modifier.padding(dimensionResource(R.dimen.xxsmall)))
        TextButton(
            onClick = {
                scope.launch{
                    drawerState.close()
                    exitProcess(0)
                }
            }
        ) {
            Row {
                Text(
                    text = stringResource(R.string.logout),
                    modifier = Modifier,
                    color = Color.Blue
                )
                Spacer(Modifier.padding(dimensionResource(R.dimen.xxsmall)))
                Icon(
                    painterResource(id = R.drawable.ic_logout),
                    contentDescription = "Logout"
                )
            }
        }
    }
}

@Composable
fun NavigationItemList(
    navigationItemViewModel: NavigationItemViewModel,
    navController: NavHostController = rememberNavController(),
    scope: CoroutineScope,
    drawerState: DrawerState,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(440.dp)
            .padding(dimensionResource(R.dimen.medium))
    ) {
        val navigationItemUiState by navigationItemViewModel.uiState.collectAsState()
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
                    selected = navigationItemUiState.currentPage == item.page,
                    onClick = {
                        val currentDestination = navigationItemUiState.currentPage
                        if (currentDestination != item.page) {
                            navigationItemViewModel.selectPage(item.page)
                        }
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

@Composable
fun ImageGrid() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(dimensionResource(R.dimen.medium)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
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
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }

            }
        }
    }
}

@Composable
fun MenuHeader() {
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
}






