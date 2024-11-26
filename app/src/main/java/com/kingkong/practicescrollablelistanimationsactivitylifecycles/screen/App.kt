package com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.Routes
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.enumClass.AppNavType
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.enumClass.Page
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.viewmodel.HomeScreenViewModel
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.viewmodel.NavigationItemViewModel
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SuperHeroApp(
//    homeScreenViewModel: HomeScreenViewModel = viewModel(),
//    navController: NavHostController = rememberNavController(),
//    navigationItemViewModel: NavigationItemViewModel = viewModel(),
//    windowSize: WindowWidthSizeClass
//
//) {
////    val hero = Hero(
////        R.string.hero1,
////        R.string.description1,
////        R.drawable.android_superhero1
////    )
////    Scaffold(
////        modifier = Modifier.fillMaxSize(),
////        topBar = { SuperHeroAppTopBar() }
////    ) { paddingValues ->
////        Column(modifier = Modifier.padding(paddingValues)) {
////            val hero = hero//HeroDataResource.heroList.getOrNull(0) // Ensure hero exists
////            //hero?.let {
//////                ScrollableHeroListItem(hero = it, modifier = Modifier.fillMaxWidth())
//////            }
////            ScrollableHeroListItem(hero = hero)
////        }
////    }
//    //        val heroes = HeroDataResource.heroList
//    //        ScrollableHeroList(heroes = heroes, modifier = Modifier.padding(it))
//
//    var isMenuClicked by rememberSaveable { mutableStateOf(false) }
//    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
//    val backStackEntry by navController.currentBackStackEntryAsState()
//    val currentScreen = backStackEntry?.destination?.route?: Routes.HERO_LIST
//    val navigationType: AppNavType
//    when (windowSize) {
//        WindowWidthSizeClass.Compact -> {
//            navigationType = AppNavType.BOTTOM_NAVIGATION
//        }
//        WindowWidthSizeClass.Medium -> {
//            navigationType = AppNavType.NAVIGATION_RAIL
//        }
//        WindowWidthSizeClass.Expanded -> {
//            navigationType = AppNavType.PERMANENT_NAVIGATION_DRAWER
//        }
//        else -> {
//            navigationType = AppNavType.BOTTOM_NAVIGATION
//        }
//    }
//
//    ModelNavigationDrawer(
//        isMenuClicked = isMenuClicked,
//        onDrawerClose = { isMenuClicked = false }
//    ){
//        Scaffold(
//            modifier = Modifier
//                .fillMaxSize(),
//            topBar = {
//                SuperHeroAppTopBar(
//                    scrollBehavior,
//                    currentScreen = currentScreen,
//                    canNavigateBack = navController.previousBackStackEntry != null,
//                    navigateUp = {navController.navigateUp()},
//                    onMenuClicked = { isMenuClicked = !isMenuClicked }
//                )
//            },
//            bottomBar = {
//                if (navigationType == AppNavType.BOTTOM_NAVIGATION) {
//                    BottomNavigationBar(navigationItemViewModel)
//                }
//            }
//        ) { innerPadding ->
//            val uiState by homeScreenViewModel.uiState.collectAsState()
//            NavHost(
//                navController = navController,
//                startDestination = Routes.HERO_LIST
//            ) {
//                composable(route = Routes.HERO_LIST) {
//                    ScrollableHeroList(
//                        homeScreenViewModel,
//                        modifier = Modifier.padding(innerPadding),
//                        onHeroSelected = { heroId ->
//                            navController.navigate("hero_profile/$heroId")
//                        }
//                    )
//                }
//                composable(
//                    route = Routes.HERO_PROFILE,
//                    arguments = listOf(navArgument("heroId") { type = NavType.IntType })
//                ) { backStackEntry ->
//                    val heroId = backStackEntry.arguments?.getInt("heroId") ?: 0
//                    HeroProfile(
//                        homeScreenViewModel,
//                        modifier = Modifier.padding(innerPadding),
//                        heroId = heroId
//                    )
//                }
//                //        val heroes = HeroDataResource.heroList
//                //        ScrollableHeroList(heroes = heroes, modifier = Modifier.padding(it))
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperHeroApp(
    homeScreenViewModel: HomeScreenViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    navigationItemViewModel: NavigationItemViewModel = viewModel(),
    windowSize: WindowWidthSizeClass,
    navigationViewModel: NavigationItemViewModel = viewModel()
) {
    val bottomNavUiState by navigationViewModel.uiState.collectAsState()
    var isMenuClicked by rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: Routes.HERO_LIST
    val navigationType = when (windowSize) {
        WindowWidthSizeClass.Compact -> AppNavType.BOTTOM_NAVIGATION
        WindowWidthSizeClass.Medium -> AppNavType.NAVIGATION_RAIL
        WindowWidthSizeClass.Expanded -> AppNavType.PERMANENT_NAVIGATION_DRAWER
        else -> AppNavType.BOTTOM_NAVIGATION
    }

    ModelNavigationDrawer(
        isMenuClicked = isMenuClicked,
        onDrawerClose = { isMenuClicked = false },
        navigationItemViewModel = navigationItemViewModel
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                SuperHeroAppTopBar(
                    scrollBehavior = scrollBehavior,
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() },
                    onMenuClicked = { isMenuClicked = !isMenuClicked }
                )
            },
            bottomBar = {
                if (navigationType == AppNavType.BOTTOM_NAVIGATION) {
                    BottomNavigationBar(navigationViewModel)
                }
            }
        ) { innerPadding ->
            val uiState by homeScreenViewModel.uiState.collectAsState()
            var startDestination = when(bottomNavUiState.currentPage){
                Page.PROFILE -> Routes.PROFILE
                Page.EXPLORE-> Routes.EXPLORE
                Page.FAVORITES -> Routes.FAVORITES
                else->Routes.HERO_LIST
            }
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable(route = Routes.HERO_LIST) {
                    ScrollableHeroList(
                        homeScreenViewModel,
                        modifier = Modifier.padding(innerPadding),
                        onHeroSelected = { heroId ->
                            navController.navigate("hero_profile/$heroId")
                        }
                    )
                }
                composable(
                    route = Routes.HERO_PROFILE,
                    arguments = listOf(navArgument("heroId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val heroId = backStackEntry.arguments?.getInt("heroId") ?: 0
                    HeroProfile(
                        homeScreenViewModel,
                        modifier = Modifier.padding(innerPadding),
                        heroId = heroId
                    )
                }
                composable(route = Routes.PROFILE){
                    UserProfile()
                }
                composable(route = Routes.FAVORITES){
                    Favorites()
                }
                composable(route = Routes.EXPLORE){
                    Explore()
                }
            }

        }
    }
}
