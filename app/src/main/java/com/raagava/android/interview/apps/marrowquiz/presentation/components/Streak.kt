package com.raagava.android.interview.apps.marrowquiz.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raagava.android.interview.apps.marrowquiz.R

@Composable
fun Streak(
    modifier: Modifier = Modifier,
    streak: Int = 0,
    minStreakThreshold: Int = 3
) {
    Row(
        modifier = modifier,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_streak),
            contentDescription = "Streak",
            modifier = Modifier.size(24.dp),
            colorFilter = if (streak >= minStreakThreshold) null else ColorFilter.colorMatrix(
                ColorMatrix().apply {
                    setToSaturation(
                        0f
                    )
                })
        )
        if (streak > 0) {
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = streak.toString(), fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun StreakPreview() {
    Streak(streak = 10)
}