package com.example.githubrepo.data

import com.example.githubrepo.data.model.CommitsModel
import com.example.githubrepo.data.model.GithubUser
import com.example.githubrepo.data.model.GithubUserResponse
import com.example.githubrepo.data.model.UserRepos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    //get github users who have more than 1 repository
    @GET("search/users?q=repos:>1")
    suspend fun getUsersList(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): Response<GithubUserResponse>

    //https://api.github.com/users/username/repos
    @GET("users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String
    ): Response<List<UserRepos>>

    //https://api.github.com/repos/username/reponame/commits
    @GET("repos/{owner}/{repo}/commits")
    suspend fun getRepoCommitInfo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<List<CommitsModel>>
}