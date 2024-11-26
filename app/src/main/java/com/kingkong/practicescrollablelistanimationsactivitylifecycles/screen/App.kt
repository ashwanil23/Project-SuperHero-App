package com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.Routes
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.enumClass.AppNavType
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
    var isDarkTheme by rememberSaveable { mutableStateOf(false) }
    MaterialTheme( colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme() ){
        ModelNavigationDrawer(
            isMenuClicked = isMenuClicked,
            navController = navController,
            onDrawerClose = { isMenuClicked = false },
            navigationItemViewModel = navigationItemViewModel
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    SuperHeroAppTopBar(
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = { isDarkTheme = it },
                        scrollBehavior = scrollBehavior,
                        currentScreen = currentScreen,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        navigateUp = { navController.navigateUp() },
                        onMenuClicked = { isMenuClicked = !isMenuClicked }
                    )
                },
                bottomBar = {
                    if (navigationType == AppNavType.BOTTOM_NAVIGATION) {
                        BottomNavigationBar(navigationViewModel, isDarkTheme = isDarkTheme)
                    }
                }
            ) { innerPadding ->
                MyAppNavigation(
                    modifier = Modifier.padding(innerPadding),
                    homeScreenViewModel,
                    navController,
                    navigationViewModel
                )
            }
        }
    }
}
