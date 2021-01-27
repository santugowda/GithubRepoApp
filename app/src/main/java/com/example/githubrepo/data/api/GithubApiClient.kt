package com.example.githubrepo.data.api

import com.example.githubrepo.data.base.Resource
import com.example.githubrepo.data.model.GithubUserModel
import com.example.githubrepo.data.model.GithubUserResponseModel
import com.example.githubrepo.data.model.UserRepos

interface GithubApiClient {

    suspend fun getUsersList(page: Int, pageSize: Int): Resource<GithubUserResponseModel>

    suspend fun getUserInfo(username: String): Resource<GithubUserModel>

    suspend fun getUserRepos(username: String): Resource<List<UserRepos>>
}