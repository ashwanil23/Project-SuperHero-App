package com.kingkong.practicescrollablelistanimationsactivitylifecycles

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.model.Hero
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen.HeroProfile
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen.ModelNavigationDrawer
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.ui.theme.PracticeScrollableListAnimationsActivityLifeCyclesTheme
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.viewmodel.HomeScreenViewModel
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.viewmodel.NavigationItemViewModel

private const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"Activity is Created")
        enableEdgeToEdge()
        setContent {
            PracticeScrollableListAnimationsActivityLifeCyclesTheme {
                SuperHeroApp()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Activity Started and visible to user")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Activity is on focus again")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "Activity restarting")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "Activity is no longer in focus")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Activity is not visible")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Destroyed")
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperHeroApp(
    homeScreenViewModel: HomeScreenViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    navigationItemViewModel: NavigationItemViewModel = viewModel()
) {
//    val hero = Hero(
//        R.string.hero1,
//        R.string.description1,
//        R.drawable.android_superhero1
//    )
//    Scaffold(
//        modifier = Modifier.fillMaxSize(),
//        topBar = { SuperHeroAppTopBar() }
//    ) { paddingValues ->
//        Column(modifier = Modifier.padding(paddingValues)) {
//            val hero = hero//HeroDataResource.heroList.getOrNull(0) // Ensure hero exists
//            //hero?.let {
////                ScrollableHeroListItem(hero = it, modifier = Modifier.fillMaxWidth())
////            }
//            ScrollableHeroListItem(hero = hero)
//        }
//    }
        //        val heroes = HeroDataResource.heroList
        //        ScrollableHeroList(heroes = heroes, modifier = Modifier.padding(it))

    var isMenuClicked by rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route?: Routes.HERO_LIST
    ModelNavigationDrawer(
        isMenuClicked = isMenuClicked,
        onDrawerClose = { isMenuClicked = false }
    ){
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                SuperHeroAppTopBar(
                    scrollBehavior,
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = {navController.navigateUp()},
                    onMenuClicked = { isMenuClicked = !isMenuClicked }
                )
            }
        ) { innerPadding ->
            val uiState by homeScreenViewModel.uiState.collectAsState()
            NavHost(
                navController = navController,
                startDestination = Routes.HERO_LIST
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
                //        val heroes = HeroDataResource.heroList
                //        ScrollableHeroList(heroes = heroes, modifier = Modifier.padding(it))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperHeroAppTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    currentScreen: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    onMenuClicked: () -> Unit
) {

    var notificationClicked by rememberSaveable {
        mutableStateOf(false)
    }
    var isMenuClicked by rememberSaveable {
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
            IconButton(onClick = {onMenuClicked()}) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    )

}



@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun ScrollableHeroListItemPreview() {
    val hero = Hero(
        id = 1,
        R.string.hero1,
        R.string.description1,
        R.drawable.android_superhero1,
        additionalImages = listOf(R.drawable.image1, R.drawable.image2, R.drawable.image3)
    )
    PracticeScrollableListAnimationsActivityLifeCyclesTheme {
        ScrollableHeroListItem(hero = hero, onClick = {})
    }
}
