package com.raagava.android.interview.apps.marrowquiz.data.local

import com.raagava.android.interview.apps.marrowquiz.data.models.PastModuleAttempt
import com.raagava.android.interview.apps.marrowquiz.data.models.QuestionDto
import com.raagava.android.interview.apps.marrowquiz.data.models.QuizAttemptEntity
import com.raagava.android.interview.apps.marrowquiz.data.models.QuizModuleDto
import com.raagava.android.interview.apps.marrowquiz.data.models.QuizUserAnswerEntity
import com.raagava.android.interview.apps.marrowquiz.domain.models.Question

class QuestionsCache(
    private val quizDB: QuizDatabase
) {

    //    private val questions: MutableList<QuestionDto> = mutableListOf()

    private val modulesData: MutableList<QuizModuleDto> = mutableListOf()
    private val questionsData: HashMap<String, List<QuestionDto>> = HashMap()

//    private val pastAttempts: HashMap<String, PastModuleAttempt> =
//        HashMap<String, PastModuleAttempt>()
//
//    private val quizUserAnswers: HashMap<String, List<Int?>> = HashMap()

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

    suspend fun storeAttempt(
        moduleId: String,
        questions: List<Question>,
        total: Int,
        correct: Int
    ) {
        quizDB.attemptsDao().insert(
            QuizAttemptEntity(
                moduleId = moduleId,
                totalQuestion = total,
                correct = correct
            )
        )
        questions.filter {
            it.userAnswerIndex != null
        }.map { q ->
            QuizUserAnswerEntity(
                moduleId = moduleId,
                questionId = q.id,
                answerIndex = q.userAnswerIndex!!
            )
        }.forEach {
            quizDB.answersDao().insert(it)
        }
//        setQuizUserAnswers(moduleId, answers)
    }

    suspend fun getPastAttempts(): HashMap<String, PastModuleAttempt> {
        val map = HashMap<String, PastModuleAttempt>()
        quizDB.attemptsDao().getAll().forEach {
            map.put(
                it.moduleId,
                PastModuleAttempt(
                    it.totalQuestion,
                    it.correct
                )
            )
        }
        return map
    }

    suspend fun getQuizUserAnswers(moduleId: String): Map<Int, Int?> {
        val map = HashMap<Int, Int?>()
        quizDB.answersDao().getAnswers(moduleId)?.map {
            map.put(
                it.questionId,
                it.answerIndex
            )
        }

        return map
    }
}
