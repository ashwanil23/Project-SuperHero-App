//package com.kingkong.practicescrollablelistanimationsactivitylifecycles
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.kingkong.practicescrollablelistanimationsactivitylifecycles.model.Hero
//import com.kingkong.practicescrollablelistanimationsactivitylifecycles.model.HeroDataResource
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//data class HomeScreenUiState(
//    val heroes: List<Hero> = HeroDataResource.heroList
//)
//class HomeScreenViewModel: ViewModel() {
//    private val _uiState = MutableStateFlow(HomeScreenUiState())
//    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()
//    init {
//        loadHeroes()
//    }
//    private fun loadHeroes() {
//        // Simulate loading data
//        _uiState.value = HomeScreenUiState(heroes = HeroDataResource.heroList)
//    }
//}