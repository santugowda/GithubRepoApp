package com.example.githubrepo.data

import com.example.githubrepo.data.model.GithubUserModel
import com.example.githubrepo.data.model.GithubUserResponseModel
import com.example.githubrepo.data.model.UsersRepoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/users?q=repos:>1")
    suspend fun getUsersList(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): Response<GithubUserResponseModel>

    @GET("users/{username}")
    suspend fun getUserInfo(
        @Path("username") username: String
    ): Response<GithubUserModel>

    //    https://api.github.com/users/santugowda/repos
    @GET("users/{username}/repos")
    suspend fun getUsersRepos(
        @Path("username") username: String
    ): Response<UsersRepoModel>

    //    https://api.github.com/repos/santugowda/ServiceNow_Project/commits
    @GET("repos/{owner}/{repo}/commits")
    suspend fun getUsersCommitInfo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<GithubUserModel>
}