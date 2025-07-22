package com.raagava.android.interview.apps.marrowquiz.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ErrorView(
    modifier: Modifier = Modifier.fillMaxSize(),
    title: String,
    description: String,
    actionButton: @Composable () -> Unit
) {

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = description, fontSize = 14.sp)

        actionButton.invoke()
    }
}