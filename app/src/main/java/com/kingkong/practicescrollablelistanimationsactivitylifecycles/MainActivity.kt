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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.model.Hero
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen.HeroProfile
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.ui.theme.PracticeScrollableListAnimationsActivityLifeCyclesTheme
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.viewmodel.HomeScreenViewModel

private const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"Activity is Created")
        enableEdgeToEdge()
        setContent {
            PracticeScrollableListAnimationsActivityLifeCyclesTheme {
                if(!isSystemInDarkTheme()){

                }
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


@Composable
fun SuperHeroApp(
    homeScreenViewModel: HomeScreenViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
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
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            SuperHeroAppTopBar()
        }
    ){innerPadding ->
        val uiState by homeScreenViewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = Routes.HERO_LIST
        ) {
            composable(route = Routes.HERO_LIST){
                ScrollableHeroList(
                    homeScreenViewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            composable(route = Routes.HERO_PROFILE){
                HeroProfile(
                    homeScreenViewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
        //        val heroes = HeroDataResource.heroList
        //        ScrollableHeroList(heroes = heroes, modifier = Modifier.padding(it))
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperHeroAppTopBar() {

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
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
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
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer))

}



@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun ScrollableHeroListItemPreview() {
    val hero = Hero(
        R.string.hero1,
        R.string.description1,
        R.drawable.android_superhero1
    )
    PracticeScrollableListAnimationsActivityLifeCyclesTheme {
        ScrollableHeroListItem(hero = hero)
    }
}
