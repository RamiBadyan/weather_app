package com.example.project.presentation.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun StyledAQIText(value: String, unit: String) {

    // Example: The value is (125 or 0.55, etc.) AND the unit is (PM2.5 or SO2, etc.).
    // This will create a style like this: 125 PM2.5, but the unit (PM2.5, etc.) will be smaller in size and gray in color.
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontWeight = FontWeight.W700
            )
        ) {
            append(value)
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onTertiary,
                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                fontWeight = FontWeight.W700
            )
        ) {
            append(" $unit")
        }
    }

    Text(
        modifier = Modifier.padding(4.dp),
        text = annotatedString
    )

}