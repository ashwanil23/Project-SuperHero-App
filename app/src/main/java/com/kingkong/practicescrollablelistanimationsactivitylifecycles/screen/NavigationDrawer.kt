package com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.enumClass.UserRole
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.viewmodel.NavigationItemViewModel
import kotlinx.coroutines.launch


@Composable
fun ModelNavigationDrawer(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    isMenuClicked: Boolean,
    onDrawerClose: () -> Unit,
    navigationItemViewModel: NavigationItemViewModel = viewModel(),
    content: @Composable () -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
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

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
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

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperHeroAppTopBar(
    isDarkTheme: Boolean,
    onToggleTheme: (Boolean) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    currentScreen: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    onMenuClicked: () -> Unit
) {

    var notificationClicked by rememberSaveable {
        mutableStateOf(false)
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    CenterAlignedTopAppBar(
        title = { Text(
            text = "Super Hero",
            style = MaterialTheme.typography.displayMedium,
        )
        },navigationIcon = {
            if (canNavigateBack){
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            }else{
                IconButton(onClick = {onMenuClicked()}) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Open Menu"
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { notificationClicked = !notificationClicked }) {
                if (!notificationClicked)Icon(imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    modifier = Modifier.size(28.dp)
                )
                else Icon(imageVector = Icons.Filled.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.Red
                )
            }
            ThemeToggleButton( isDarkTheme = isDarkTheme, onToggleTheme = onToggleTheme)

//            IconButton(onClick = {onMenuClicked()}) {
//                Icon(
//                    imageVector = Icons.Filled.Menu,
//                    contentDescription = "Localized description"
//                )
//            }
        },
        scrollBehavior = scrollBehavior,
        colors = when(isDarkTheme){
            true -> TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            else -> TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                actionIconContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                navigationIconContentColor = MaterialTheme.colorScheme.inverseOnSurface)
        }

    )
}

@Composable
fun ThemeToggleButton(
    isDarkTheme: Boolean,
    onToggleTheme: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.small)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Switch(
            checked = isDarkTheme,
            onCheckedChange = onToggleTheme,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.inverseSurface,
                checkedTrackColor = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.5f),
                uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            ),
            thumbContent = {
                val imageVector: ImageVector = if (isDarkTheme) {
                    Icons.Filled.Brightness4
                } else {
                    Icons.Filled.Brightness7
                }
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )
    }
}




