package com.kingkong.practicescrollablelistanimationsactivitylifecycles.viewmodel

import androidx.lifecycle.ViewModel
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.enumClass.UserRole
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.model.NavigationItem
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.model.NavigationItemResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class NavigationItemUiState(
    val items: List<NavigationItem> = NavigationItemResource.navigationItems
)
class NavigationItemViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NavigationItemUiState())
    val uiState: StateFlow<NavigationItemUiState> = _uiState.asStateFlow()
    init {
        loadItems(UserRole.REGULAR_USER)
    }

    fun loadItems(userRole: UserRole) {
        val filteredItems = NavigationItemResource.navigationItems.filter {
            it.roles.contains(userRole)
        }
        _uiState.value = NavigationItemUiState(items = filteredItems)
    }
}
