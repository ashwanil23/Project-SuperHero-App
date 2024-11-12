package com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.R
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.model.Hero
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.viewmodel.HomeScreenViewModel

@Composable
fun HeroProfile(
    viewModel: HomeScreenViewModel = viewModel(),
    modifier: Modifier = Modifier,
    heroId: Int,
) {
    val hero = viewModel.getHeroById(heroId)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = dimensionResource(R.dimen.xxsmall),
                horizontal = dimensionResource(R.dimen.small)
            )
    ) {
        if (hero != null) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(32.dp)
            ) {
                HeroMainContent(hero)
                HeroAdditionalImages(hero.additionalImages)
            }
        } else {
            Text("Hero not found", style = MaterialTheme.typography.displaySmall)
        }
    }
}

@Composable
fun HeroMainContent(hero: Hero) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = hero.imageRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(dimensionResource(R.dimen.small)))
                .background(MaterialTheme.colorScheme.surface)
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium)))
        Text(
            text = stringResource(id = hero.nameRes),
            style = MaterialTheme.typography.displaySmall
        )
        Text(
            text = stringResource(id = hero.descriptionRes),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium)))
    }
}

@Composable
fun HeroAdditionalImages(images: List<Int>) {
    Row(
        modifier = Modifier
            .height(128.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        images.forEach { item ->
            Image(
                painter = painterResource(id = item),
                contentDescription = null,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.small))
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.small)))
                    .background(MaterialTheme.colorScheme.surface)
            )
        }
    }
}

