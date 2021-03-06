package com.example.githubrepo.data.api

import com.example.githubrepo.data.base.Resource
import com.example.githubrepo.data.model.CommitsModel
import com.example.githubrepo.data.model.GithubUser
import com.example.githubrepo.data.model.GithubUserResponse
import com.example.githubrepo.data.model.UserRepos

interface GithubApiClient {

    suspend fun getUsersList(page: Int, pageSize: Int): Resource<GithubUserResponse>

    suspend fun getUserRepos(username: String): Resource<List<UserRepos>>

    suspend fun getCommits(owner: String, repo : String): Resource<List<CommitsModel>>
}