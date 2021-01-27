package com.example.githubrepo.data.model

import com.google.gson.annotations.SerializedName

data class CommitsModel(
    @SerializedName("sha") val sha: String,
    @SerializedName("commit") val commit: Commit
)

data class Commit (
    @SerializedName("sha") val hash: String,
    @SerializedName("author") val author: Author,
    @SerializedName("committer") val committer: Committer,
    @SerializedName("tree") val tree: Tree,
    @SerializedName("message") val message: String  //required
)


data class Author(
    @SerializedName("name") val name: String,  // required
    @SerializedName("email") val email: String,
    @SerializedName("date") val date: String

)

data class Committer(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("date") val date: String
)

data class Tree(
    @SerializedName("sha") val sha: String, //required
    @SerializedName("url") val url: String
)
