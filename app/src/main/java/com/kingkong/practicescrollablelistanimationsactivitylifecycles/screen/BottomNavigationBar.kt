package com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.R
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.enumClass.Page
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.viewmodel.NavigationItemViewModel

@Composable
fun BottomNavigationBar(navigationViewModel: NavigationItemViewModel,isDarkTheme: Boolean) {
    val navigationUiState by navigationViewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
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
                        contentDescription = "Hero List",
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
