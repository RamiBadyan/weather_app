package com.example.project.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.project.R
import com.example.project.core.Functions
import com.example.project.domain.model.current_weather.CurrentWeather
import com.example.project.ui.theme.shadowColor

@Composable
fun CurrentWeatherCard(data: CurrentWeather, icon: Int) {

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
                text = stringResource(R.string.now),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.W500
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Text(
                        text = "${data.main.temp.toInt()}°",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.W500
                        )
                    )
                    Text(
                        text = "C",
                        style = MaterialTheme.typography.displaySmall.copy(
                        ),
                        modifier = Modifier.padding(horizontal = 2.dp)
                    )
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(icon),
                        modifier = Modifier.size(70.dp),
                        contentDescription = null,
                    )
                }
            }
            Text(text = data.weather[0].description, style = MaterialTheme.typography.bodyLarge)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = MaterialTheme.colorScheme.onTertiary,
                thickness = 0.8.dp
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.DateRange, contentDescription = null)

                Text(
                    text = Functions.formatDate(data.dt),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = null)

                Text(
                    text = data.name + ", " + data.sys.country,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }

        }
    }
}