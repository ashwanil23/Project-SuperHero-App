package com.kingkong.practicescrollablelistanimationsactivitylifecycles.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.enumClass.Page
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.enumClass.UserRole

data class NavigationItem(
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int,
    val page: Page,
    val route: String,
    val roles: List<UserRole> = listOf(UserRole.REGULAR_USER, UserRole.ADMIN),
)

data class BottomNavItem(
    val title: Int,
    val icon: Int,
    val route: String
)
