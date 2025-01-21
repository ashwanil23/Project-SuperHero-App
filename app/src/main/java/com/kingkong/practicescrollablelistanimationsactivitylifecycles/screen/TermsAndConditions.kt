package com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.R

@Composable
fun TermsAndConditionsScreen(
    onAgree: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier
        .padding(dimensionResource(R.dimen.medium))
    ) {
        item {
            Text(
                text = stringResource(R.string.Terms_Conditions_Title),
            )
            Text(
                text = stringResource(id = R.string.terms_conditions),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = onAgree) {
                    Text(text = stringResource(id = R.string.agree))
                }
                OutlinedButton(onClick = onCancel) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            }
        }
    }
}