package com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.R
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.enumClass.Page
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.viewmodel.NavigationItemViewModel

@Composable
fun BottomNavigationBar(navigationViewModel: NavigationItemViewModel,isDarkTheme: Boolean) {
    val navigationUiState by navigationViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(R.dimen.Large)),
    ) {
        BottomNavigation(
            backgroundColor = when(isDarkTheme){
                true -> MaterialTheme.colorScheme.surface
                else -> MaterialTheme.colorScheme.surface
            },
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            BottomNavigationItem(
                selected = navigationUiState.currentPage == Page.HERO_LIST,
                onClick = { navigationViewModel.selectPage(Page.HERO_LIST) },
                icon = {
                    Icon(
                        painterResource(id = R.drawable.ic_list),
                        contentDescription = "Hero List"
                    )
                },
                label = { Text("Hero List") }
            )
            BottomNavigationItem(
                selected = navigationUiState.currentPage == Page.EXPLORE,
                onClick = { navigationViewModel.selectPage(Page.EXPLORE) },
                icon = {
                    Icon(
                        painterResource(id = R.drawable.ic_explore),
                        contentDescription = "Explore"
                    )
                },
                label = { Text("Explore") }
            )
            BottomNavigationItem(
                selected = navigationUiState.currentPage == Page.FAVORITES,
                onClick = { navigationViewModel.selectPage(Page.FAVORITES) },
                icon = {
                    Icon(
                        painterResource(id = R.drawable.ic_favorite),
                        contentDescription = "Favorites"
                    )
                },
                label = { Text("Favorites") }
            )
            BottomNavigationItem(
                selected = navigationUiState.currentPage == Page.PROFILE,
                onClick = { navigationViewModel.selectPage(Page.PROFILE) },
                icon = {
                    Icon(
                        painterResource(id = R.drawable.ic_profile),
                        contentDescription = "Profile"
                    )
                },
                label = { Text("Profile") }
            )
        }
    }
}
