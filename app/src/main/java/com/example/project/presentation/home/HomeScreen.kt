package com.example.project.presentation.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.example.project.R
import com.example.project.core.Functions
import com.example.project.core.components.InfoDialog
import com.example.project.presentation.home.components.CurrentWeatherCard
import com.example.project.core.components.ErrorMessage
import com.example.project.presentation.home.components.Footer
import com.example.project.presentation.home.components.ForecastCard
import com.example.project.presentation.home.components.HighlightsCard
import com.example.project.presentation.home.components.SearchBarScreen
import com.example.project.ui.theme.shadowColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val weatherState = viewModel.state.value
    var searchActive by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val activity = (LocalContext.current as? Activity)

    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    // When the app get launched for the first time
    LaunchedEffect(true) {
        locationPermissions.launchMultiplePermissionRequest()
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.getCurrentLocation()
    }

    BackHandler {
        if (searchActive) {
            searchActive = false
        } else {
            activity?.finish()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(8.dp),
                    clip = false,
                    ambientColor = shadowColor,
                    spotColor = shadowColor
                ),
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    Image(
                        painter = painterResource(R.drawable.logo),
                        modifier = Modifier
                            .padding(start = 16.dp, end = 10.dp)
                            .size(40.dp),
                        contentDescription = null,
                    )
                },
                actions = {
                    IconButton(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        onClick = {
                            searchActive = !searchActive
                        }) {
                        Icon(Icons.Filled.Search, contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        // FAB for user location
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(10.dp),
                containerColor =  MaterialTheme.colorScheme.primaryContainer,
                onClick = { viewModel.getCurrentLocation() },
                content = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.my_location),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = 10.dp,
                    end = 10.dp,
                ),
            contentAlignment = Alignment.Center
        ) {

            if (weatherState.currentWeather != null) {
                val icon = Functions.getIcon(weatherState.currentWeather.weather[0].icon)

                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .fillMaxSize()
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(10.dp))

                    CurrentWeatherCard(weatherState.currentWeather, icon)

                    Spacer(modifier = Modifier.height(6.dp))

                    if (weatherState.forecast != null) {
                        ForecastCard(weatherState.forecast)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (weatherState.airPollution != null) {
                        HighlightsCard(weatherState.airPollution, weatherState.currentWeather)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Footer()

                    Spacer(modifier = Modifier.height(50.dp))
                }
            } else if (weatherState.error != null) {
                ErrorMessage(
                    message = weatherState.error,
                    onClickTryAgain = { viewModel.getCurrentLocation() }
                )
            } else if (weatherState.loading) {
                CircularProgressIndicator(color =  MaterialTheme.colorScheme.onPrimary)
            }

            if (viewModel.askForEnableLocation && weatherState.currentWeather == null) {
                ErrorMessage(
                    message = stringResource(R.string.desc_info_dialog),
                    textButton = stringResource(R.string.title_info_dialog),
                    onClickTryAgain = {
                        viewModel.getCurrentLocation()
                    }
                )
            }

            if (weatherState.infoDialog) {
                InfoDialog(
                    title = stringResource(R.string.title_info_dialog),
                    desc = stringResource(R.string.desc_info_dialog),
                    onDismiss = {
                        viewModel.dismissDialog()
                        viewModel.askForEnableLocation = true
                    },
                    onClick = {
                        viewModel.dismissDialog()
                        viewModel.enableLocation()
                    }
                )
            }
        }
    }

    if (searchActive) {
        SearchBarScreen (
            viewModel = viewModel
        ) {
            searchActive = false
        }
    }

}