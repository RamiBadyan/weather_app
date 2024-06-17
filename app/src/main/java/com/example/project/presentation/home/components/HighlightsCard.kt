package com.example.project.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.project.R
import com.example.project.core.Functions
import com.example.project.domain.model.air_pollution.AirPollution
import com.example.project.domain.model.current_weather.CurrentWeather
import com.example.project.ui.theme.shadowColor

@Composable
fun HighlightsCard(airPollution: AirPollution, currentWeather: CurrentWeather) {

    val (text, color) = Functions.aQIToTextAndColor(airPollution.list[0].main.aqi)
    val components = airPollution.list[0].components

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .shadow(
                elevation = 50.dp,
                shape = RoundedCornerShape(10.dp),
                clip = false,
                ambientColor = shadowColor,
                spotColor = shadowColor
            ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {

        Column(modifier = Modifier.padding(20.dp)) {

            Text(
                modifier = Modifier
                    .padding(vertical = 4.dp),
                text = stringResource(R.string.today_highlights),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.W500
                ),
            )

            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = stringResource(R.string.air_quality_index),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .background(color)
                            .padding(vertical = 6.dp, horizontal = 12.dp),
                        color = Color.Black,
                        style = MaterialTheme.typography.labelMedium,
                        text = text
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.air_24),
                        modifier = Modifier.size(40.dp),
                        contentDescription = null,
                    )

                    Column {
                        StyledAQIText(value = components.pm2_5.toString(), unit = "PM2.5")
                        StyledAQIText(value = components.no2.toString(), unit = "NO2")
                    }

                    Column {
                        StyledAQIText(value = components.so2.toString(), unit = "SO2")
                        StyledAQIText(value = components.o3.toString(), unit = "O3")
                    }
                }
            }


            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = stringResource(R.string.sunrise_sunset),
                    style = MaterialTheme.typography.titleSmall
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.sun),
                            modifier = Modifier
                                .padding(14.dp)
                                .size(30.dp),
                            contentDescription = null,
                        )

                        Column {
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = stringResource(R.string.sunrise),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = Functions.formatTime(currentWeather.sys.sunrise),
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.moon),
                            modifier = Modifier
                                .padding(14.dp)
                                .size(30.dp),
                            contentDescription = null,
                        )
                        Column {
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = stringResource(R.string.sunset),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = Functions.formatTime(currentWeather.sys.sunset),
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                    }

                }
            }

            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = stringResource(R.string.humidity_pressure),
                    style = MaterialTheme.typography.titleSmall
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.humidity),
                            modifier = Modifier
                                .padding(14.dp)
                                .size(30.dp),
                            contentDescription = null,
                        )

                        Column {
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = stringResource(R.string.humidity),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = "${currentWeather.main.humidity}%",
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.pressure),
                            modifier = Modifier
                                .padding(14.dp)
                                .size(30.dp),
                            contentDescription = null,
                        )
                        Column {
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = stringResource(R.string.pressure),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = "${currentWeather.main.pressure}hPa",
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                    }

                }
            }

            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = stringResource(R.string.visibility_feels_like),
                    style = MaterialTheme.typography.titleSmall
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.visibility),
                            modifier = Modifier
                                .padding(14.dp)
                                .size(30.dp),
                            contentDescription = null,
                        )

                        Column {
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = stringResource(R.string.visibility),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = "${currentWeather.visibility / 1000}km",
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.temperature),
                            modifier = Modifier
                                .padding(14.dp)
                                .size(30.dp),
                            contentDescription = null,
                        )
                        Column {
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = stringResource(R.string.feels_like),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = "${currentWeather.main.feels_like.toInt()}\u00B0C",
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                    }

                }
            }

        }
    }
}