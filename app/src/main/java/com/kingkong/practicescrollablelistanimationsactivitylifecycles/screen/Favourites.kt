package com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioNoBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.R
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.model.Hero
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.ui.theme.PracticeScrollableListAnimationsActivityLifeCyclesTheme
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.viewmodel.HomeScreenViewModel

@Composable
fun Favorites(
    homeScreenViewModel: HomeScreenViewModel = viewModel(),
    modifier: Modifier,
    onHeroSelected: (Int) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val homeScreenUiState by homeScreenViewModel.uiState.collectAsState()
    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    val density = LocalDensity.current

    // LazyListState that survives configuration changes
    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }

    // Remove the re-scrolling on each recomposition
    AnimatedVisibility(
        visibleState = visibleState,
        enter = slideInVertically {
            with(density) { -40.dp.roundToPx() }
        } + expandVertically(expandFrom = Alignment.Top) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        LazyColumn(
            modifier = modifier,
            state = listState,
            contentPadding = contentPadding
        ) {
            if(homeScreenUiState.favorites.isEmpty()){
                item{
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "This list is empty",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }else{
                itemsIndexed(//heroes
                    homeScreenUiState.favorites) { index, hero ->
                    FavoriteListItem(
                        hero = hero,
                        onClick = {onHeroSelected(hero.id)},
                        onFavoriteClick = {heroId -> homeScreenViewModel.toggleFavorite(hero.id)},
                        modifier = Modifier
                            .padding(
                                horizontal = dimensionResource(R.dimen.xsmall),
                                vertical = dimensionResource(R.dimen.small)
                            )
                            .animateEnterExit(
                                enter = slideInVertically(
                                    animationSpec = spring(
                                        stiffness = StiffnessVeryLow,
                                        dampingRatio = DampingRatioNoBouncy
                                    )
                                    //initialOffsetY = { it * (index + 1) }
                                )
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteListItem(
    modifier: Modifier = Modifier,
    hero: Hero,
    onClick:()-> Unit,
    onFavoriteClick: (Hero) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(
                vertical = dimensionResource(R.dimen.xxsmall),
                horizontal = dimensionResource(R.dimen.small)
            )
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.xsmall))
    ) {
        AnimatedContent(
            targetState = expanded,
            transitionSpec = {
                fadeIn(animationSpec = tween(150, 150)) togetherWith
                        fadeOut(animationSpec = tween(150)) using
                        SizeTransform { initialSize, targetSize ->
                            if (targetState) {
                                keyframes {
                                    // Expand horizontally first.
                                    IntSize(targetSize.width, initialSize.height) at 150
                                    durationMillis = 300
                                }
                            } else {
                                keyframes {
                                    // Shrink vertically first.
                                    IntSize(initialSize.width, targetSize.height) at 150
                                    durationMillis = 300
                                }
                            }
                        }
            }, label = "size transform"
        ) { targetExpanded ->
            if (targetExpanded) {
                Log.i(TAG,"Expanded")
                FavoriteExpandedHeroContent(hero = hero,onClick,onFavoriteClick)

            } else {
                FavoriteHeroIconContent(hero = hero)
                Log.i(TAG,"Not Expanded")
            }
        }
    }
}

@Composable
fun FavoriteHeroIconContent(
    hero: Hero
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.medium))
            .sizeIn(minHeight = 72.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.medium)))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            androidx.compose.material3.Text(
                text = stringResource(id = hero.nameRes),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.displaySmall
            )
        }
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.medium)))
        Image(
            painter = painterResource(id = hero.imageRes),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(dimensionResource(R.dimen.small)))
        )
    }
}

@Composable
fun FavoriteExpandedHeroContent(
    hero: Hero,
    onClick: () -> Unit,
    onFavoriteClick: (Hero) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.medium))
    ) {
        Image(
            painter = painterResource(id = hero.imageRes),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimensionResource(R.dimen.small)))
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.small)))
        androidx.compose.material3.Text(
            text = stringResource(id = hero.nameRes),
            style = MaterialTheme.typography.displaySmall
        )
        androidx.compose.material3.Text(
            text = stringResource(id = hero.descriptionRes),
            style = MaterialTheme.typography.bodyLarge
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = onClick
            ) {
                Text("Explore ${stringResource(id = hero.nameRes)}")
            }
            Spacer(Modifier.weight(1f))
            Icon(
                painter = painterResource(
                    id = if
                                 (hero.isFav) R.drawable.ic_star_filled
                    else
                        R.drawable.ic_star_outline
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onFavoriteClick(hero) },
                tint = if (hero.isFav) Color.Magenta else Color.Gray
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FavoriteHeroListItemPreviewHomeScreen() {
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



