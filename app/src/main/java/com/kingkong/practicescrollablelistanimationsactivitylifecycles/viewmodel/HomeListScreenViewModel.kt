package com.kingkong.practicescrollablelistanimationsactivitylifecycles.viewmodel

import androidx.lifecycle.ViewModel
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.model.Hero
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.model.HeroDataResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeScreenUiState(
    val heroes: List<Hero> = HeroDataResource.heroList,
    val favorites: List<Hero> = emptyList(),
    val isShowingHomepage: Boolean = true
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

    fun toggleFavorite(heroId: Int){
        val currentHeroes = _uiState.value.heroes
        val updatedHeroes = currentHeroes.map{
            hero->
            if(hero.id == heroId) hero.copy(isFav = !hero.isFav) else hero
        }
        val favoritesHeroes = updatedHeroes.filter { it.isFav }
        _uiState.value = _uiState.value.copy(heroes = updatedHeroes, favorites = favoritesHeroes)
    }
}