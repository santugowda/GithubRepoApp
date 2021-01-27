package com.example.githubrepo.data.api

import com.example.githubrepo.data.GithubApi
import com.example.githubrepo.data.base.Resource
import com.example.githubrepo.data.model.CommitsModel
import com.example.githubrepo.data.model.GithubUserModel
import com.example.githubrepo.data.model.GithubUserResponseModel
import com.example.githubrepo.data.model.UserRepos
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
                Resource.error("${ex.message}")
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
            Resource.error("${ex.message}")
        }
    }

    override suspend fun getUserRepos(username: String): Resource<List<UserRepos>> =
        withContext(
            Dispatchers.IO) {
            try {
                val response = githubApi.getUserRepos(username)
                if (response.isSuccessful) {
                    Resource.success(response.body())

                } else {
                    Resource.error(response.message())
                }
            } catch (ex: Throwable) {
                Resource.error("${ex.message}")
            }
        }


    override suspend fun getCommits(owner: String, repo:String ): Resource<List<CommitsModel>> = withContext(Dispatchers.IO) {
        try {
            val response = githubApi.getRepoCommitInfo(owner, repo)
            if (response.isSuccessful) {
                Resource.success(response.body())

            } else {
                Resource.error(response.message())
            }
        } catch (ex: Throwable) {
            Resource.error("${ex.message}")
        }
    }
}