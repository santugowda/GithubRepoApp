package com.example.githubrepo.data.api

import com.example.githubrepo.data.GithubApi
import com.example.githubrepo.data.base.Resource
import com.example.githubrepo.data.model.GithubUserModel
import com.example.githubrepo.data.model.GithubUserResponseModel
import com.example.githubrepo.data.model.UsersRepoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubApiClientImpl(private val githubApi: GithubApi) : GithubApiClient {

    override suspend fun getUsersList(page: Int, pageSize: Int): Resource<GithubUserResponseModel> =
        withContext(
            Dispatchers.IO
        ) {
            try {
                val response = githubApi.getUsersList(page, pageSize)
                if (response.isSuccessful) {
                    Resource.success(response.body())

                } else {
                    Resource.error(response.message())
                }
            } catch (ex: Throwable) {
                Resource.error<GithubUserResponseModel>("${ex.message}")
            }
        }

    override suspend fun getUserInfo(username: String): Resource<GithubUserModel> = withContext(
        Dispatchers.IO
    ) {
        try {
            val response = githubApi.getUserInfo(username)
            if (response.isSuccessful) {
                Resource.success(response.body())

            } else {
                Resource.error(response.message())
            }
        } catch (ex: Throwable) {
            Resource.error<GithubUserModel>("${ex.message}")
        }
    }

    override suspend fun getUsersRepos(username: String): Resource<UsersRepoModel> =
        withContext(
            Dispatchers.IO) {
            try {
                val response = githubApi.getUsersRepos(username)
                if (response.isSuccessful) {
                    Resource.success(response.body())

                } else {
                    Resource.error(response.message())
                }
            } catch (ex: Throwable) {
                Resource.error<UsersRepoModel>("${ex.message}")
            }
        }
}