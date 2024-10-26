package com.kingkong.practicescrollablelistanimationsactivitylifecycles

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.model.Hero
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.ui.theme.PracticeScrollableListAnimationsActivityLifeCyclesTheme

//@Composable
//fun ScrollableHeroList(
//    modifier: Modifier = Modifier,
//    heroes: List<Hero>
//) {
//
//}
//
//@Composable
//fun ScrollableHeroListItem(
//    modifier: Modifier = Modifier,
//    hero: Hero
//) {
//    Card(modifier = modifier.padding(8.dp), elevation = CardDefaults.cardElevation(4.dp)) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//                .sizeIn(minHeight = 72.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Column(
//                modifier = Modifier.weight(1f)
//            ) {
//                Text(
//                    text = stringResource(id = hero.nameRes),
//                    fontWeight = FontWeight.Bold,
//                    color = Color.White
//                )
//                Text(
//                    text = stringResource(id = hero.descriptionRes),
//                )
//            }
//            Spacer(modifier = Modifier.width(16.dp))
//            Image(
//                painter = painterResource(id = hero.imageRes),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(72.dp)
//                    .clip(RoundedCornerShape(8.dp))
//            )
//        }
//    }
//}
private const val TAG = "MainActivity"
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScrollableHeroList(
    //heroes: List<Hero>,
    homeScreenViewModel: HomeScreenViewModel = viewModel(),
    modifier: Modifier = Modifier,
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
            itemsIndexed(//heroes
                homeScreenUiState.heroes) { index, hero ->
                ScrollableHeroListItem(
                    hero = hero,
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(R.dimen.xsmall), vertical = dimensionResource(R.dimen.small))
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
@Composable
fun ScrollableHeroListItem(
    modifier: Modifier = Modifier,
    hero: Hero
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(vertical = dimensionResource(R.dimen.xxsmall), horizontal = dimensionResource(R.dimen.small))
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
                ExpandedHeroContent(hero = hero)

            } else {
                HeroIconContent(hero = hero)
                Log.i(TAG,"Not Expanded")
            }
        }
    }
}

@Composable
fun HeroIconContent(hero: Hero) {
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
            Text(
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
fun ExpandedHeroContent(hero: Hero) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.medium))
    ) {
        Text(
            text = stringResource(id = hero.nameRes),
            style = MaterialTheme.typography.displaySmall
        )
        Text(
            text = stringResource(id = hero.descriptionRes),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.small)))
        Image(
            painter = painterResource(id = hero.imageRes),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimensionResource(R.dimen.small)))
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ScrollableHeroListItemPreviewHomeScreen() {
    val hero = Hero(
        R.string.hero1,
        R.string.description1,
        R.drawable.android_superhero1
    )
    PracticeScrollableListAnimationsActivityLifeCyclesTheme {
        ScrollableHeroListItem(hero = hero)
    }
}


