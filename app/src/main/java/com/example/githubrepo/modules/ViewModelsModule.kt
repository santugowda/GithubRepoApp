package com.example.githubrepo.modules

import com.example.githubrepo.presentation.viewmodel.UserViewModel
import com.example.githubrepo.presentation.viewmodel.UsersListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val usersListViewModel = module {
    viewModel { UsersListViewModel(get()) }
}

val userViewModel = module {
    viewModel { UserViewModel(get()) }
}