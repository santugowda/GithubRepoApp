package com.example.githubrepo

import android.app.Application
import com.example.githubrepo.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(
                listOf(
                    githubApiModule,
                    githubApiClientModule,
                    usersListDataSourceFactory,
                    usersListViewModel,
                    userViewModel
                )
            )
        }
    }

}