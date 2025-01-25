package com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.Routes
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.enumClass.Page
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.viewmodel.HomeScreenViewModel
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.viewmodel.NavigationItemViewModel

@Composable
fun MyAppNavigation(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel,
    navController: NavHostController = rememberNavController(),
    navigationViewModel: NavigationItemViewModel = viewModel()
){
    val bottomNavUiState by navigationViewModel.uiState.collectAsState()
    val uiState by homeScreenViewModel.uiState.collectAsState()
    val startDestination = when(bottomNavUiState.currentPage){
        Page.PROFILE -> Routes.PROFILE
        Page.EXPLORE-> Routes.EXPLORE
        Page.FAVORITES -> Routes.FAVORITES
        Page.SETTINGS -> Routes.SETTINGS
        Page.ABOUT -> Routes.ABOUT
        Page.FEEDBACK-> Routes.FEEDBACK
        Page.NOTIFICATIONS -> Routes.NOTIFICATIONS
        Page.ACHIEVEMENTS -> Routes.ACHIEVEMENTS
        Page.TERMS_AND_CONDITIONS -> Routes.TERMS_AND_CONDITIONS
        else-> Routes.HERO_LIST
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Routes.HERO_LIST) {
            ScrollableHeroList(
                homeScreenViewModel,
                modifier = modifier,
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
                modifier = modifier,
                heroId = heroId
            )
        }
        composable(route = Routes.PROFILE){
            UserProfile(modifier = modifier)
        }
        composable(route = Routes.FAVORITES){
            Favorites(
                homeScreenViewModel,
                modifier = modifier,
                onHeroSelected = { heroId ->
                    navController.navigate("hero_profile/$heroId")
                }
            )
        }
        composable(route = Routes.EXPLORE){
            Explore(modifier = modifier)
        }
        composable(route = Routes.SETTINGS){
            Settings(modifier = modifier)
        }
        composable(route = Routes.ABOUT){
            About(modifier = modifier)
        }
        composable(route = Routes.FEEDBACK){
            FeedBack(modifier = modifier)
        }
        composable(route = Routes.NOTIFICATIONS){
            Notifications(modifier = modifier)
        }
        composable(route = Routes.ACHIEVEMENTS){
            Achievements(modifier = modifier)
        }
        composable(route = Routes.TERMS_AND_CONDITIONS){
            TermsAndConditionsScreen(
                modifier = modifier,
                onAgree = { navController.navigate("hero_list") },
                onCancel = { navController.popBackStack() }
            )
        }
    }
}