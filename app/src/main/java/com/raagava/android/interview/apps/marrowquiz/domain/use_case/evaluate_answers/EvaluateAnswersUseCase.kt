package com.raagava.android.interview.apps.marrowquiz.domain.use_case.evaluate_answers

import com.raagava.android.interview.apps.marrowquiz.domain.models.Question
import com.raagava.android.interview.apps.marrowquiz.domain.models.QuizResult

class EvaluateAnswersUseCase {

    operator fun invoke(questions: List<Question>): QuizResult {
        var correct = 0
        var incorrect = 0
        var skipped = 0

        var maxStreak = 0
        var minStreak = 0

        questions.forEach { question ->
            val answer = question.userAnswerIndex
            if (answer == null) {
                skipped++
                minStreak = 0
            } else {
                val isCorrect = question.correctOptionIndex == answer
                if (isCorrect) {
                    correct++
                    minStreak += 1

                    if (minStreak > maxStreak) {
                        maxStreak = minStreak
                    }
                } else {
                    incorrect++
                    minStreak = 0
                }
            }
        }
        return QuizResult(
            total = questions.size,
            correct = correct,
            incorrect = incorrect,
            skipped = skipped,
            highestStreak = maxStreak
        )
    }
}