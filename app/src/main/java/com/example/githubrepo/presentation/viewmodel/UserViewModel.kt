package com.example.githubrepo.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepo.data.api.GithubApiClient
import com.example.githubrepo.data.base.Status
import com.example.githubrepo.data.model.UserRepos
import kotlinx.coroutines.launch

class UserViewModel(private val githubApiClient: GithubApiClient) : ViewModel() {

    val isWaiting: ObservableField<Boolean> = ObservableField()
    val errorMessage: ObservableField<String> = ObservableField()
    val githubUserRepos: ObservableField<List<UserRepos>> = ObservableField()
    var usersRepoLiveData: MutableLiveData<List<UserRepos>> = MutableLiveData()

    init {
        isWaiting.set(true)
        errorMessage.set(null)
    }

    fun getUserRepos(username: String): MutableLiveData<List<UserRepos>> {
        viewModelScope.launch {
            val result = githubApiClient.getUserRepos(username)
            if (result.status == Status.SUCCESS) {
                githubUserRepos.set(result.data)
                errorMessage.set(null)
                usersRepoLiveData.value = result.data

            } else {
                githubUserRepos.set(null)
                errorMessage.set(result.message)
                usersRepoLiveData.value = result.data
            }
            isWaiting.set(false)
        }

        return usersRepoLiveData
    }
}