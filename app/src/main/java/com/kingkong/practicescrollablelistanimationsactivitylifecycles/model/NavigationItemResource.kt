package com.kingkong.practicescrollablelistanimationsactivitylifecycles.model

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.R
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.enumClass.UserRole

object NavigationItemResource {
    val navigationItems = listOf(
        NavigationItem(
            titleRes = R.string.home,
            iconRes = R.drawable.ic_home,
            roles = listOf(UserRole.ADMIN, UserRole.REGULAR_USER)
        ) {
            // Navigate to Home screen
        },

        NavigationItem(
            titleRes = R.string.hero_list,
            iconRes = R.drawable.ic_list,
            roles = listOf(UserRole.ADMIN, UserRole.REGULAR_USER)
        ) {
            // Navigate to Hero List screen
        },

        NavigationItem(
            titleRes = R.string.favorites,
            iconRes = R.drawable.ic_favorite,
            roles = listOf(UserRole.REGULAR_USER)
        ) {
            // Navigate to Favorites screen
        },

        NavigationItem(
            titleRes = R.string.explore,
            iconRes = R.drawable.ic_explore,
            roles = listOf(UserRole.REGULAR_USER)
        ) {
            // Navigate to Explore screen
        },

        NavigationItem(
            titleRes = R.string.profile,
            iconRes = R.drawable.ic_profile,
            roles = listOf(UserRole.ADMIN, UserRole.REGULAR_USER)
        ) {
            // Navigate to Profile screen
        },

        NavigationItem(
            titleRes = R.string.settings,
            iconRes = R.drawable.ic_settings,
            roles = listOf(UserRole.ADMIN)
        ) {
            // Navigate to Settings screen
        },

        NavigationItem(
            titleRes = R.string.about,
            iconRes = R.drawable.ic_info,
            roles = listOf(UserRole.ADMIN, UserRole.REGULAR_USER, UserRole.GUEST)
        ) {
            // Navigate to About screen
        },

        NavigationItem(
            titleRes = R.string.feedback,
            iconRes = R.drawable.ic_feedback,
            roles = listOf(UserRole.ADMIN, UserRole.REGULAR_USER, UserRole.GUEST)
        ) {
            // Navigate to Feedback screen
        },

        NavigationItem(
            titleRes = R.string.notifications,
            iconRes = R.drawable.ic_notifications,
            roles = listOf(UserRole.ADMIN, UserRole.REGULAR_USER)
        ) {
            // Navigate to Notifications screen
        },

        NavigationItem(
            titleRes = R.string.achievements,
            iconRes = R.drawable.ic_achievements,
            roles = listOf(UserRole.REGULAR_USER)
        ) {
            // Navigate to Achievements screen
        },

        NavigationItem(
            titleRes = R.string.logout,
            iconRes = R.drawable.ic_logout,
            roles = listOf(UserRole.ADMIN, UserRole.REGULAR_USER)
        ) {
            // Handle Logout
        }
    )
}
