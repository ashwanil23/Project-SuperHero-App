package com.kingkong.practicescrollablelistanimationsactivitylifecycles

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.model.Hero
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen.ScrollableHeroListItem
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen.SuperHeroApp
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.ui.theme.PracticeScrollableListAnimationsActivityLifeCyclesTheme

private const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"Activity is Created")
        enableEdgeToEdge()
        setContent {
            PracticeScrollableListAnimationsActivityLifeCyclesTheme {
                val windowSize = calculateWindowSizeClass(this)
                SuperHeroApp(
                    windowSize = windowSize.widthSizeClass
                )
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
@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun ScrollableHeroListItemPreview() {
    val hero = Hero(
        id = 1,
        R.string.hero1,
        R.string.description1,
        R.drawable.android_superhero1,
        additionalImages = listOf(R.drawable.image1, R.drawable.image2, R.drawable.image3),
        isFav = false
    )
    PracticeScrollableListAnimationsActivityLifeCyclesTheme {
        ScrollableHeroListItem(hero = hero, onClick = {}, onFavoriteClick = {})
    }
}
