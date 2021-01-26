package com.example.githubrepo.presentation.datasource.userList

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.githubrepo.data.api.GithubApiClient
import com.example.githubrepo.data.model.GithubUserModel

class UsersListDataSourceFactory(private val githubApiClient: GithubApiClient): DataSource.Factory<Int, GithubUserModel>() {

    val liveData: MutableLiveData<UsersListDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, GithubUserModel> {
        val usersListDataSource = UsersListDataSource(githubApiClient)
        liveData.postValue(usersListDataSource)
        return usersListDataSource
    }

}