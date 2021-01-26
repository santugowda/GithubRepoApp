package com.example.githubrepo.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepo.data.api.GithubApiClient
import com.example.githubrepo.data.base.Status
import com.example.githubrepo.data.model.UsersRepoModel
import kotlinx.coroutines.launch

class UsersRepoViewModel(private val githubApiClient: GithubApiClient) : ViewModel() {

    val isWaiting: ObservableField<Boolean> = ObservableField()
    val errorMessage: ObservableField<String> = ObservableField()
    val githubUserModel: ObservableField<UsersRepoModel> = ObservableField()
    val pageUrl: MutableLiveData<String> = MutableLiveData()

    init {
        isWaiting.set(true)
        errorMessage.set(null)
    }

    fun getUsersRepos(username: String) {
        viewModelScope.launch {
            val result = githubApiClient.getUsersRepos(username)
            if (result.status == Status.SUCCESS) {
                githubUserModel.set(result.data)
                errorMessage.set(null)

            } else {
                githubUserModel.set(null)
                errorMessage.set(result.message)
            }

            isWaiting.set(false)
        }
    }
}