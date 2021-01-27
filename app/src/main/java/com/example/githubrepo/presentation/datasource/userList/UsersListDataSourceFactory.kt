package com.example.githubrepo.presentation.datasource.userList

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.githubrepo.data.api.GithubApiClient
import com.example.githubrepo.data.model.GithubUser

class UsersListDataSourceFactory(private val githubApiClient: GithubApiClient): DataSource.Factory<Int, GithubUser>() {

    val liveData: MutableLiveData<UsersListDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, GithubUser> {
        val usersListDataSource = UsersListDataSource(githubApiClient)
        liveData.postValue(usersListDataSource)
        return usersListDataSource
    }

}