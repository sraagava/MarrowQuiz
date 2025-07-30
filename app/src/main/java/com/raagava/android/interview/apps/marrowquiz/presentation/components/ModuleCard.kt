package com.raagava.android.interview.apps.marrowquiz.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raagava.android.interview.apps.marrowquiz.domain.models.QuizModule

@Composable
fun ModuleCard(
    modifier: Modifier,
    quizModule: QuizModule,
    onClick: (moduleId: String) -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = quizModule.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = quizModule.description)

            quizModule.pastAttempt?.let { attempt ->
                Spacer(Modifier.height(8.dp))
                Row(Modifier.weight(1f)) {
                    Text("${attempt.totalQuestions} Questions | Score: ${attempt.score}/${attempt.totalQuestions}")
                }
            }
        }
        Button(
            onClick = {
                onClick.invoke(quizModule.id)
            }
        ) {
            Text(
                text = if (quizModule.pastAttempt != null) {
                    "Review"
                } else {
                    "Start"
                }
            )
        }
    }
}

@Preview
@Composable
fun ModuleCardPreview() {
    ModuleCard(
        modifier = Modifier,
        quizModule = QuizModule(
            id = "1",
            title = "Title",
            description = "Description",
            pastAttempt = null
        ),
        onClick = {}
    )
}