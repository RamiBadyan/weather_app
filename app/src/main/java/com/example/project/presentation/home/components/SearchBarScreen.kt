package com.example.project.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.project.R
import com.example.project.presentation.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarScreen(
    viewModel: HomeViewModel,
    onClose: () -> Unit,
) {

    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(true) }
    val searchState = viewModel.searchState.value

    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.primary),
        query = query,
        onQueryChange = {
            query = it
            viewModel.searchForLocation(query)
        },
        onSearch = {
            viewModel.searchForLocation(query)
        },
        active = active,
        onActiveChange = {
            active = it
            if (!it) {
                onClose()
            }
        },
        placeholder = {
            Text(
                text = stringResource(R.string.search),
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary)
        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier = Modifier.clickable {
                        if (query.isNotEmpty()) {
                            query = ""
                        } else {
                            onClose()
                        }
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            if (searchState.geos.isNotEmpty()) {
                Column {
                    searchState.geos.forEach {
                        SearchItem(it) { geo ->
                            onClose()
                            viewModel.changeLocation(geo.lat, geo.lon)
                        }
                        HorizontalDivider(
                            thickness = 0.8.dp,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                }
            } else if (searchState.loading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else if (query.isNotEmpty()
                && (searchState.error != null)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    text = searchState.error,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}