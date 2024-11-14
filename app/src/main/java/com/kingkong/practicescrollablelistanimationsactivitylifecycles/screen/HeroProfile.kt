package com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
    val hero = viewModel.getHeroById(heroId)
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
                HeroMainContent(hero)
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


//fun saveImage(context: Context, drawableId: Int, fileName: String) {
//    val drawable = context.getDrawable(drawableId)
//    val bitmap = (drawable as BitmapDrawable).bitmap
//
//    val file = File(context.getExternalFilesDir(null), "$fileName.jpg")
//
//    try {
//        val outputStream = FileOutputStream(file)
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
//        outputStream.flush()
//        outputStream.close()
//    } catch (e: IOException) {
//        e.printStackTrace()
//    }
//}
//
//
//@Composable
//fun ImageDialog(imageRes: Int, onDismiss: () -> Unit) {
//    val context = LocalContext.current
//    var showPermissionDialog by remember { mutableStateOf(false) }
//
//    Dialog(onDismissRequest = onDismiss) {
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Black.copy(alpha = 0.8f))
//                .clickable { onDismiss() }
//        ) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Image(
//                    painter = painterResource(id = imageRes),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .aspectRatio(1f)
//                        .clip(RoundedCornerShape(dimensionResource(R.dimen.small)))
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//                Button(onClick = {
//                    val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
//                        saveImage(context, imageRes, "downloaded_image")
//                    } else {
//                        ActivityCompat.requestPermissions(context as Activity, arrayOf(permission), 0)
//                    }
//                }) {
//                    Text("Download Image")
//                }
//            }
//        }
//    }
//
//    if (showPermissionDialog) {
//        AlertDialog(
//            onDismissRequest = { showPermissionDialog = false },
//            title = { Text("Storage Permission Required") },
//            text = { Text("Please grant storage permission to download the image.") },
//            confirmButton = {
//                Button(onClick = {
//                    showPermissionDialog = false
//                    ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
//                }) {
//                    Text("Grant Permission")
//                }
//            },
//            dismissButton = {
//                Button(onClick = { showPermissionDialog = false }) {
//                    Text("Cancel")
//                }
//            }
//        )
//    }
//}
