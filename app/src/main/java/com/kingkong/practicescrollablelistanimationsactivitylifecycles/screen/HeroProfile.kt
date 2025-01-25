package com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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
    val heroUiState by viewModel.uiState.collectAsState()
    val hero = heroUiState.heroes.find { it.id == heroId }
    var selectedImage by remember { mutableStateOf<Int?>(null) }
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
                    .padding(dimensionResource(R.dimen.large))
            ) {
                HeroMainContent(hero, onFavoriteClick = {viewModel.toggleFavorite(heroId)})
                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.inverseSurface
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium)))
                HeroAdditionalImages(hero.additionalImages) { imageRes -> selectedImage = imageRes }
            }
        } else {
            Text("Hero not found", style = MaterialTheme.typography.displaySmall)
        }
    }
    selectedImage?.let { imageRes -> ImageDialog(imageRes) { selectedImage = null } }

}

@Composable
fun HeroMainContent(hero: Hero,onFavoriteClick: (Hero)-> Unit) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
            .fillMaxSize(),
        ) {
            Image(
                painter = painterResource(id = hero.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.small)))
                    .background(MaterialTheme.colorScheme.surface)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                IconButton(
                    onClick = {onFavoriteClick(hero)},
                    enabled = true,
                    colors = IconButtonDefaults.iconButtonColors(Color.Transparent)
                ) {
                    Icon(
                        painter = painterResource(
                            id =
                            if(hero.isFav)
                                R.drawable.ic_star_filled
                            else
                                R.drawable.ic_star_outline
                        ),
                        contentDescription =
                        if(hero.isFav)
                           "Add to Favorite"
                        else
                            "Remover From Favorite",
                        tint = if (hero.isFav) Color.Magenta else Color.Gray
                    )
                }
                IconButton(
                    onClick = {},
                    enabled = true,
                    colors = IconButtonDefaults.iconButtonColors(Color.Transparent)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Comment,
                        contentDescription = "Add Comment",
                        tint = Color.White
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium)))
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.inverseSurface
            )
            Text(
                text = stringResource(id = hero.nameRes),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.small))
            )
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.inverseSurface
            )
        }
        Text(
            text = stringResource(id = hero.descriptionRes),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium)))
    }
}

@Composable
fun HeroAdditionalImages(images: List<Int>, onClick: (Int) -> Unit) {
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
                    .clickable { onClick(item) }
            )
        }
    }
}

@Composable
fun ImageDialog(imageRes: Int, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box( contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f))
                .clickable { onDismiss() } ) {
            Image( painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.small)))
            )
        }
    }
}

