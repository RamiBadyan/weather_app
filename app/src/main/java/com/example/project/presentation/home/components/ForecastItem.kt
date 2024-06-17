package com.example.project.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.project.core.Functions
import com.example.project.domain.model.forecast.CurrentForecast

@Composable
fun ForecastItem(item: CurrentForecast) {

    Column(
        modifier = Modifier
            .padding(6.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${item.main.temp.toInt()}°",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )
        Image(
            painter = painterResource(Functions.getIcon(item.weather[0].icon)),
            modifier = Modifier
                .padding(vertical = 6.dp)
                .size(30.dp),
            contentDescription = null,
        )
        Text(
            text = Functions.formatDateToWeekAndHour(item.dt),
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}