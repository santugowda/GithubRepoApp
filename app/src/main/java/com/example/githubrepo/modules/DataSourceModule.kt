package com.example.githubrepo.modules

import com.example.githubrepo.presentation.datasource.userList.UsersListDataSourceFactory
import org.koin.dsl.module

val usersListDataSourceFactory = module {
    single { UsersListDataSourceFactory(get()) }
}