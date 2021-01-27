package com.example.githubrepo.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepo.data.api.GithubApiClient
import com.example.githubrepo.data.base.Status
import com.example.githubrepo.data.model.CommitsModel
import com.example.githubrepo.data.model.UserRepos
import kotlinx.coroutines.launch

class UserViewModel(private val githubApiClient: GithubApiClient) : ViewModel() {

    val isWaiting: ObservableField<Boolean> = ObservableField()
    val errorMessage: ObservableField<String> = ObservableField()
    private val githubUserRepos: ObservableField<List<UserRepos>> = ObservableField()
    private var usersRepoLiveData: MutableLiveData<List<UserRepos>> = MutableLiveData()
    private val repoCommitsModel: ObservableField<List<CommitsModel>> = ObservableField()
    private var repoCommitLiveData: MutableLiveData<List<CommitsModel>> = MutableLiveData()

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
                if(result.data.isNullOrEmpty()){
                    errorMessage.set("No repos for this user")
                } else {
                    errorMessage.set(result.message)
                }
            }
            isWaiting.set(false)
        }

        return usersRepoLiveData
    }


    fun getRepoCommits(owner: String, repo : String) : MutableLiveData<List<CommitsModel>> {
        viewModelScope.launch {
            val result = githubApiClient.getCommits(owner, repo)
            if (result.status == Status.SUCCESS) {
                repoCommitsModel.set(result.data)
                errorMessage.set(null)
                repoCommitLiveData.value = result.data
            } else {
                repoCommitsModel.set(null)
                if(result.data.isNullOrEmpty()){
                    errorMessage.set("No commits for this repo")
                } else {
                    errorMessage.set(result.message)
                }
            }
            isWaiting.set(false)
        }

        return repoCommitLiveData
    }
}