package com.example.githubrepo.data.model

import com.google.gson.annotations.SerializedName

data class UserRepos(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("description") val description: String?,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("stargazers_count") val stars: Int
)

data class Owner(
    @SerializedName("login") val login: String,
    @SerializedName("url") val url: String?,
    @SerializedName("avatar_url") var avatarUrl: String
)
