package com.raagava.android.interview.apps.marrowquiz.presentation.screens.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.raagava.android.interview.apps.marrowquiz.domain.models.QuizResult
import com.raagava.android.interview.apps.marrowquiz.presentation.app.Screens
import com.raagava.android.interview.apps.marrowquiz.presentation.components.TopBar
import com.raagava.android.interview.apps.marrowquiz.ui.theme.CorrectColor
import com.raagava.android.interview.apps.marrowquiz.ui.theme.CorrectOptionBg
import com.raagava.android.interview.apps.marrowquiz.ui.theme.IncorrectOptionBg
import com.raagava.android.interview.apps.marrowquiz.ui.theme.SelectedOptionBg
import com.raagava.android.interview.apps.marrowquiz.ui.theme.SkippedBg

@Composable
fun ResultScreen(
    navController: NavController,
    result: QuizResult,
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TopBar(title = "Quiz Result")
        LinearProgressIndicator(
            progress = { 1f },
            modifier = Modifier.fillMaxWidth(),
            color = CorrectColor,
            trackColor = MaterialTheme.colorScheme.secondary
        )
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "Congratulations!",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "You have completed the quiz. Here's your performance summary.",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
            item {
                StatCard(
                    title = "Correct Answers",
                    stat = "${result.correct}/${result.total}",
                    modifier = Modifier
                        .weight(1f)
                        .background(CorrectOptionBg, RoundedCornerShape(8.dp))
                )
            }
            item {
                StatCard(
                    title = "Highest Streak",
                    stat = "${result.highestStreak}",
                    modifier = Modifier
                        .weight(1f)
                        .background(SelectedOptionBg, RoundedCornerShape(8.dp))
                )
            }
            item {
                StatCard(
                    title = "Incorrect Answers",
                    stat = "${result.incorrect}",
                    modifier = Modifier
                        .weight(1f)
                        .background(IncorrectOptionBg, RoundedCornerShape(8.dp))
                )
            }
            item {
                StatCard(
                    title = "Skipped Questions",
                    stat = "${result.skipped}",
                    modifier = Modifier
                        .weight(1f)
                        .background(SkippedBg, RoundedCornerShape(8.dp))
                )
            }
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Spacer(modifier = Modifier.height(40.dp))
            }
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Button(onClick = {
                    navController.navigate(Screens.QuizScreen)
                }) {
                    Text(text = "Restart Quiz")
                }
            }
        }
    }
}

@Composable
fun StatCard(title: String, stat: String, modifier: Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = stat, fontSize = 30.sp)
        Text(text = title, fontSize = 14.sp)
    }
}


@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    ResultScreen(
        navController = NavController(LocalContext.current),
        result = QuizResult(
            total = 10,
            correct = 7,
            incorrect = 3,
            skipped = 2,
            highestStreak = 5
        )
    )
}