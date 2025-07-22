package com.raagava.android.interview.apps.marrowquiz.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raagava.android.interview.apps.marrowquiz.R
import com.raagava.android.interview.apps.marrowquiz.domain.models.Question
import com.raagava.android.interview.apps.marrowquiz.ui.theme.CorrectColor
import com.raagava.android.interview.apps.marrowquiz.ui.theme.CorrectOptionBg
import com.raagava.android.interview.apps.marrowquiz.ui.theme.IncorrectColor
import com.raagava.android.interview.apps.marrowquiz.ui.theme.IncorrectOptionBg

@Composable
fun QuestionSection(
    question: Question,
    selectedOption: Int?,
    onOptionSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = question.question,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )
        question.options.forEachIndexed { index, option ->
            OptionButton(
                text = option,
                isSelected = selectedOption == index,
                isAnswer = if (selectedOption == null) false else (index == question.correctOptionIndex),
                enabled = selectedOption == null,
                onClick = { onOptionSelected(index) },
            )
        }
    }
}

@Composable
fun OptionButton(
    text: String,
    isSelected: Boolean,
    isAnswer: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val bgColor = when {
        isAnswer -> CorrectOptionBg
        !isAnswer && isSelected -> IncorrectOptionBg
        else -> MaterialTheme.colorScheme.background
    }
    val checkIconColor = when {
        isAnswer -> CorrectColor
        !isAnswer -> {
            if (isSelected) IncorrectColor else Color.Transparent
        }

        else -> Color.Transparent
    }

    val icon = if (isSelected && !isAnswer) {
        painterResource(R.drawable.ic_cancel)
    } else {
        painterResource(R.drawable.ic_check)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .clickable(enabled) {
                onClick.invoke()
            }
            .border(
                0.7.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 12.dp)
        )

        if (isSelected || isAnswer) {
            Icon(
                painter = icon,
                contentDescription = "Selected",
                tint = checkIconColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionSectionPreview() {
    QuestionSection(
        question = Question(
            question = "What is the capital of France?",
            options = listOf("Paris", "London", "Berlin", "Madrid"),
            correctOptionIndex = 0,
            id = 1,
        ),
        selectedOption = null,
        onOptionSelected = {}
    )
}