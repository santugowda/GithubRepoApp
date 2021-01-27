package com.example.githubrepo.data.model

import com.google.gson.annotations.SerializedName

data class GithubUserResponse(
    @SerializedName("total_count") var totalCount: Long,
    @SerializedName("incomplete_results") var incompleteResults: Boolean,
    @SerializedName("items") var items: List<GithubUser>
)