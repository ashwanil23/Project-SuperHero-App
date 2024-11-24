package com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.R
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.enumClass.Page
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.viewmodel.NavigationItemViewModel

@Composable
fun BottomNavigationBar(navigationViewModel: NavigationItemViewModel) {
    val navigationUiState by navigationViewModel.uiState.collectAsState()

    BottomNavigation {
        BottomNavigationItem(
            selected = navigationUiState.currentPage == Page.HOME,
            onClick = { navigationViewModel.selectPage(Page.HOME) },
            icon = { Icon(painterResource(id = R.drawable.ic_home), contentDescription = "Home") },
            label = { Text("Home") }
        )
        BottomNavigationItem(
            selected = navigationUiState.currentPage == Page.HERO_LIST,
            onClick = { navigationViewModel.selectPage(Page.HERO_LIST) },
            icon = { Icon(painterResource(id = R.drawable.ic_list), contentDescription = "Hero List") },
            label = { Text("Hero List") }
        )
        BottomNavigationItem(
            selected = navigationUiState.currentPage == Page.EXPLORE,
            onClick = { navigationViewModel.selectPage(Page.EXPLORE) },
            icon = { Icon(painterResource(id = R.drawable.ic_explore), contentDescription = "Explore") },
            label = { Text("Explore") }
        )
        BottomNavigationItem(
            selected = navigationUiState.currentPage == Page.FAVORITES,
            onClick = { navigationViewModel.selectPage(Page.FAVORITES) },
            icon = { Icon(painterResource(id = R.drawable.ic_favorite), contentDescription = "Favorites") },
            label = { Text("Favorites") }
        )
        BottomNavigationItem(
            selected = navigationUiState.currentPage == Page.PROFILE,
            onClick = { navigationViewModel.selectPage(Page.PROFILE) },
            icon = { Icon(painterResource(id = R.drawable.ic_profile), contentDescription = "Profile") },
            label = { Text("Profile") }
        )
    }
}
