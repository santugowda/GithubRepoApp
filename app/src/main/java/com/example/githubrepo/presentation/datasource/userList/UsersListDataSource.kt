package com.example.githubrepo.presentation.datasource.userList

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.githubrepo.data.api.GithubApiClient
import com.example.githubrepo.data.base.Status
import com.example.githubrepo.data.model.GithubUserModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class UsersListDataSource(private val githubApiClient: GithubApiClient): PageKeyedDataSource<Int, GithubUserModel>() {

    private val dataSourceJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + dataSourceJob)
    val loadStateLiveData: MutableLiveData<Status> = MutableLiveData()
    val totalCount: MutableLiveData<Long> = MutableLiveData()

    companion object{
        const val PAGE_SIZE = 15
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GithubUserModel>
    ) {
        scope.launch {
            loadStateLiveData.postValue(Status.LOADING)
            totalCount.postValue(0)

            val response = githubApiClient.getUsersList(1, PAGE_SIZE)
            when(response.status) {
                Status.ERROR -> loadStateLiveData.postValue(Status.ERROR)
                Status.EMPTY -> loadStateLiveData.postValue(Status.EMPTY)
                else -> {
                    response.data?.let {
                        callback.onResult(it.items, null, 2)
                        loadStateLiveData.postValue(Status.SUCCESS)
                        totalCount.postValue(it.totalCount)
                    }
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GithubUserModel>) {
        scope.launch {
            val response = githubApiClient.getUsersList(params.key, PAGE_SIZE)
            response.data?.let {
                callback.onResult(it.items, params.key + 1)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GithubUserModel>) {

    }
}