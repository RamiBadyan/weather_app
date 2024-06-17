package com.example.project.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project.R
import com.example.project.domain.model.forecast.Forecast
import com.example.project.ui.theme.shadowColor

@Composable
fun ForecastCard(data: Forecast) {

    Column {
        Text(
            text = stringResource(R.string._5_days_3_hour_forecast),
            modifier = Modifier
                .padding(6.dp),
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.W600
            ),
            color = MaterialTheme.colorScheme.onPrimary
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 50.dp,
                    shape = RoundedCornerShape(10.dp),
                    clip = false,
                    ambientColor = shadowColor,
                    spotColor = shadowColor
                ),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            LazyRow (
                contentPadding = PaddingValues(6.dp)
            ) {
                items(data.list) { item ->
                    ForecastItem(item)
                }
            }
        }
    }
}