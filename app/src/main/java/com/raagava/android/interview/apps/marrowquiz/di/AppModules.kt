package com.raagava.android.interview.apps.marrowquiz.di

import com.raagava.android.interview.apps.marrowquiz.data.local.QuestionsCache
import com.raagava.android.interview.apps.marrowquiz.data.remote.ApiProvider
import com.raagava.android.interview.apps.marrowquiz.data.remote.QuizApi
import com.raagava.android.interview.apps.marrowquiz.data.repository.QuizRepositoryImpl
import com.raagava.android.interview.apps.marrowquiz.domain.repository.QuizRepository
import com.raagava.android.interview.apps.marrowquiz.domain.use_case.get_questions.GetQuestionsUseCase
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

    //Local
    single {
        QuestionsCache()
    }

    //Repositories
    single<QuizRepository> {
        QuizRepositoryImpl(get(), get())
    }
}

val domainModules = module {

    //UseCase
    single { GetQuestionsUseCase(get()) }
}

val appModule = module {

}

val koinModules = listOf(
    dataModules,
    domainModules,
    appModule
)