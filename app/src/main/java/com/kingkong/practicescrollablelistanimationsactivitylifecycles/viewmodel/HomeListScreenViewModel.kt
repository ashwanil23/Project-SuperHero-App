package com.kingkong.practicescrollablelistanimationsactivitylifecycles.viewmodel

import androidx.lifecycle.ViewModel
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.model.Hero
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.model.HeroDataResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeScreenUiState(
    val heroes: List<Hero> = HeroDataResource.heroList
)
class HomeScreenViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()
    init {
        loadHeroes()
    }
    private fun loadHeroes() {
        // Simulate loading data
        _uiState.value = HomeScreenUiState(heroes = HeroDataResource.heroList)
    }

    fun getHeroById(heroId: Int): Hero? {
        return _uiState.value.heroes.find { it.id == heroId }
    }
}