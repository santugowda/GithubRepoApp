package com.example.githubrepo.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.githubrepo.BaseTest
import com.example.githubrepo.MockResponseFileReader
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection

@RunWith(MockitoJUnitRunner::class)
class GithubApiTest : BaseTest() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `read sample success json file`() {
        val reader = MockResponseFileReader("userListResponse.json")
        Assert.assertNotNull(reader)
    }

    @Test
    fun `get users list result and check response Code 200 returned`() = runBlocking {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("userListResponse.json").content)
        mockWebServer.enqueue(response)

        val actualResponse = service.getUsersList(1, 15)
        if (actualResponse.isSuccessful) {
            Assert.assertNotNull(actualResponse.body())
            Assert.assertEquals(actualResponse.code(), 200)
        }
    }

    @Test
    fun `get users list and check response Code 400 returned`() = runBlocking {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody(MockResponseFileReader("userListResponse.json").content)
        mockWebServer.enqueue(response)

        val actualResponse = service.getUsersList(1, 15)
        if (!actualResponse.isSuccessful) {
            Assert.assertEquals(actualResponse.code(), 400)
        }
    }


    @Test
    fun `get users list Items Result and check response`() = runBlocking {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("userListResponse.json").content)
        mockWebServer.enqueue(response)
        val actualResponse = service.getUsersList(1, 15)
        if (actualResponse.isSuccessful) {
            Assert.assertNotNull(actualResponse.body())
            Assert.assertEquals(actualResponse.code(), 200)
            Assert.assertEquals(actualResponse.body()?.totalCount, 15198719.toLong())
            Assert.assertEquals(actualResponse.body()?.items?.get(0)?.login, "torvalds")
            Assert.assertEquals(
                actualResponse.body()?.items?.get(0)?.url,
                "https://api.github.com/users/torvalds"
            )
        }
    }

    @Test
    fun `get repo for user and check response`() = runBlocking {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("userRepoResponse.json").content)
        mockWebServer.enqueue(response)
        val actualResponse = service.getUserRepos("santugowda")
        if (actualResponse.isSuccessful) {
            Assert.assertNotNull(actualResponse.body())
            Assert.assertEquals(actualResponse.code(), 200)
            Assert.assertEquals(actualResponse.body()?.get(0)?.name, "android-fundamentals")
            Assert.assertEquals(
                actualResponse.body()?.get(0)?.fullName,
                "santugowda/android-fundamentals"
            )
            Assert.assertEquals(actualResponse.body()?.get(0)?.owner?.login, "santugowda")
            Assert.assertEquals(
                actualResponse.body()?.get(0)?.owner?.url,
                "https://api.github.com/users/santugowda"
            )
        }
    }

    @Test
    fun `get repo commits and check response`() = runBlocking {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("repoCommitResponse.json").content)
        mockWebServer.enqueue(response)
        val actualResponse = service.getRepoCommitInfo("santugowda", "ServiceNow_Project")
        if (actualResponse.isSuccessful) {
            Assert.assertNotNull(actualResponse.body())
            Assert.assertEquals(actualResponse.code(), 200)
            Assert.assertEquals(
                actualResponse.body()?.get(0)?.sha,
                "668552133b90a35d7b1970ed1720eb439a08efce"
            )
            Assert.assertEquals(actualResponse.body()?.get(0)?.commit?.author?.name, "santugowda")
            Assert.assertEquals(
                actualResponse.body()?.get(0)?.commit?.message,
                "Merge branch 'master' of https://github.com/santugowda/android-interview-project-master"
            )
        }
    }

}