package com.raagava.android.interview.apps.marrowquiz.data.local

import com.raagava.android.interview.apps.marrowquiz.data.models.PastModuleAttempt
import com.raagava.android.interview.apps.marrowquiz.data.models.QuestionDto
import com.raagava.android.interview.apps.marrowquiz.data.models.QuizModuleDto

class QuestionsCache {

    //    private val questions: MutableList<QuestionDto> = mutableListOf()
    private val modulesData: MutableList<QuizModuleDto> = mutableListOf()
    private val questionsData: HashMap<String, List<QuestionDto>> = HashMap()

    private val pastAttempts: HashMap<String, PastModuleAttempt> =
        HashMap<String, PastModuleAttempt>()

    private val quizUserAnswers: HashMap<String, List<Int?>> = HashMap()

    fun setModules(newModules: List<QuizModuleDto>) {
        modulesData.clear()
        modulesData.addAll(newModules)
    }

    fun getModules(): List<QuizModuleDto> {
        return modulesData
    }

    fun setQuestions(moduleId: String, newQuestions: List<QuestionDto>) {
        questionsData.put(moduleId, newQuestions)
    }

    fun getQuestions(moduleId: String): List<QuestionDto>? {
        return questionsData[moduleId]
    }

    fun storeAttempt(
        moduleId: String,
        answers: List<Int?>,
        total: Int,
        correct: Int
    ) {
        pastAttempts.put(
            moduleId, PastModuleAttempt(
                totalQuestion = total,
                correct = correct
            )
        )
        setQuizUserAnswers(moduleId, answers)
    }

    fun getPastAttempts(): HashMap<String, PastModuleAttempt> {
        return pastAttempts
    }

    fun setQuizUserAnswers(moduleId: String, answers: List<Int?>) {
        quizUserAnswers.put(moduleId, answers)
    }

    fun getQuizUserAnswers(moduleId: String): List<Int?>? {
        return quizUserAnswers[moduleId]
    }
}
