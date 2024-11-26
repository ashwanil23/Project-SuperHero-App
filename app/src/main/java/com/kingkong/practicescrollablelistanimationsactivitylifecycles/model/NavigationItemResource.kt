package com.kingkong.practicescrollablelistanimationsactivitylifecycles.model

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.R
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.Routes
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.enumClass.UserRole

object NavigationItemResource {
    val navigationItems = listOf(
        NavigationItem(
            titleRes = R.string.favorites,
            iconRes = R.drawable.ic_favorite,
            route = Routes.FAVORITES,
            roles = listOf(UserRole.REGULAR_USER)
        ),
        NavigationItem(
            titleRes = R.string.explore,
            iconRes = R.drawable.ic_explore,
            route = Routes.EXPLORE,
            roles = listOf(UserRole.REGULAR_USER)
        ),
        NavigationItem(
            titleRes = R.string.profile,
            iconRes = R.drawable.ic_profile,
            route = Routes.PROFILE,
            roles = listOf(UserRole.ADMIN, UserRole.REGULAR_USER)
        ),
        NavigationItem(
            titleRes = R.string.settings,
            iconRes = R.drawable.ic_settings,
            route = Routes.SETTINGS,
            roles = listOf(UserRole.ADMIN)
        ),
        NavigationItem(
            titleRes = R.string.about,
            iconRes = R.drawable.ic_info,
            route = Routes.ABOUT,
            roles = listOf(UserRole.ADMIN, UserRole.REGULAR_USER, UserRole.GUEST)
        ),
        NavigationItem(
            titleRes = R.string.feedback,
            iconRes = R.drawable.ic_feedback,
            route = Routes.FEEDBACK,
            roles = listOf(UserRole.ADMIN, UserRole.REGULAR_USER, UserRole.GUEST)
        ),
        NavigationItem(
            titleRes = R.string.notifications,
            iconRes = R.drawable.ic_notifications,
            route = Routes.NOTIFICATIONS,
            roles = listOf(UserRole.ADMIN, UserRole.REGULAR_USER)
        ),
        NavigationItem(
            titleRes = R.string.achievements,
            iconRes = R.drawable.ic_achievements,
            route = Routes.ACHIEVEMENTS,
            roles = listOf(UserRole.REGULAR_USER)
        ),
        NavigationItem(
            titleRes = R.string.logout,
            iconRes = R.drawable.ic_logout,
            route = Routes.HERO_LIST,
            roles = listOf(UserRole.ADMIN, UserRole.REGULAR_USER)
        )
    )
}


object BottomNavItems {
    val bottomNavigationItems = listOf(
        BottomNavItem(title = R.string.home, icon = R.drawable.ic_home, route = "home"),
        BottomNavItem(title = R.string.hero_list, icon = R.drawable.ic_list, route = "hero_list"),
        BottomNavItem(title = R.string.explore, icon = R.drawable.ic_explore, route = "explore"),
        BottomNavItem(title = R.string.favorites, icon = R.drawable.ic_favorite, route = "favorites"),
        BottomNavItem(title = R.string.profile, icon = R.drawable.ic_profile, route = "profile"),
        BottomNavItem(title = R.string.support, icon = R.drawable.ic_support, route = "support"),
    )
}
