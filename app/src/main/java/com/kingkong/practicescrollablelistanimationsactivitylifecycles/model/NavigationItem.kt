package com.kingkong.practicescrollablelistanimationsactivitylifecycles.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.enumClass.UserRole

data class NavigationItem(
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int,
    val roles: List<UserRole> = listOf(UserRole.REGULAR_USER, UserRole.ADMIN),
    val onClick: () -> Unit,
)

data class BottomNavItem(
    val title: Int,
    val icon: Int,
    val route: String
)
