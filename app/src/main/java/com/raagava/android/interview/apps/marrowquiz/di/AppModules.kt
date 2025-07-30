package com.raagava.android.interview.apps.marrowquiz.di

import androidx.room.Room
import com.raagava.android.interview.apps.marrowquiz.data.local.QuestionsCache
import com.raagava.android.interview.apps.marrowquiz.data.local.QuizDatabase
import com.raagava.android.interview.apps.marrowquiz.data.remote.ApiProvider
import com.raagava.android.interview.apps.marrowquiz.data.remote.QuizApi
import com.raagava.android.interview.apps.marrowquiz.data.repository.QuizRepositoryImpl
import com.raagava.android.interview.apps.marrowquiz.domain.repository.QuizRepository
import com.raagava.android.interview.apps.marrowquiz.domain.use_case.evaluate_answers.EvaluateAnswersUseCase
import com.raagava.android.interview.apps.marrowquiz.domain.use_case.get_questions.GetQuestionsUseCase
import com.raagava.android.interview.apps.marrowquiz.domain.use_case.get_quiz_modules.GetQuizModulesUseCase
import com.raagava.android.interview.apps.marrowquiz.domain.use_case.store_quiz_attempt.StoreQuizAttemptUseCase
import com.raagava.android.interview.apps.marrowquiz.presentation.screens.module_list.ModuleListViewModel
import com.raagava.android.interview.apps.marrowquiz.presentation.screens.quiz.QuizViewModel
import com.raagava.android.interview.apps.marrowquiz.presentation.screens.splash.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModules = module {

    //Retrofit
    single<Retrofit> {
        ApiProvider().getRetrofit()
    }
    single<QuizApi> {
        get<Retrofit>().create<QuizApi>(QuizApi::class.java)
    }

    single<QuizDatabase> {
        Room.databaseBuilder(
            get(),
            QuizDatabase::class.java, "quiz_database"
        ).build()
    }
    //Local
    single {
        QuestionsCache(get())
    }

    //Repositories
    single<QuizRepository> {
        QuizRepositoryImpl(get(), get())
    }
}

val domainModules = module {

    //UseCase
    single { GetQuestionsUseCase(get()) }
    single { EvaluateAnswersUseCase() }
    single { GetQuizModulesUseCase(get()) }
    single { StoreQuizAttemptUseCase(get()) }
}

val appModule = module {

    //ViewModel
    viewModel {
        SplashViewModel(get())
    }
    viewModel {
        QuizViewModel(get(), get(), get())
    }
    viewModel {
        ModuleListViewModel(get())
    }
}

val koinModules = listOf(
    dataModules,
    domainModules,
    appModule
)