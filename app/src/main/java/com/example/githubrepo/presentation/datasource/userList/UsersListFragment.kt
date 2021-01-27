package com.example.githubrepo.presentation.datasource.userList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepo.R
import com.example.githubrepo.data.base.Status
import com.example.githubrepo.data.model.GithubUser
import com.example.githubrepo.databinding.FragmentUsersListBinding
import com.example.githubrepo.presentation.viewmodel.UsersListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersListFragment : Fragment(), UsersListAdapter.OnUsersSelected {

    private val usersListViewModel: UsersListViewModel by viewModel()
    private lateinit var usersItemsViewer: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding : FragmentUsersListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_users_list, container, false)
        binding.usersListViewModel = usersListViewModel

        usersItemsViewer = binding.root.findViewById(R.id.usersItemsViewer)
        initAdapterAndObserve()
        return binding.root
    }

    private fun initAdapterAndObserve() {
        val usersListAdapter = UsersListAdapter(this)
        usersItemsViewer.layoutManager = LinearLayoutManager(context)
        usersItemsViewer.adapter = usersListAdapter

        Transformations.switchMap(usersListViewModel.dataSource) { dataSource -> dataSource.loadStateLiveData }
            .observe(viewLifecycleOwner, Observer {
                when(it) {
                    Status.LOADING -> {
                        usersListViewModel.isWaiting.set(true)
                        usersListViewModel.errorMessage.set(null)
                    }
                    Status.SUCCESS -> {
                        usersListViewModel.isWaiting.set(false)
                        usersListViewModel.errorMessage.set(null)
                    }
                    Status.EMPTY -> {
                        usersListViewModel.isWaiting.set(false)
                        usersListViewModel.errorMessage.set(getString(R.string.msg_users_list_is_empty))
                    }
                    else -> {
                        usersListViewModel.isWaiting.set(false)
                        usersListViewModel.errorMessage.set(getString(R.string.msg_fetch_users_list_has_error))
                    }
                }
            })

        usersListViewModel.usersLiveData.observe(viewLifecycleOwner, Observer {
            usersListAdapter.submitList(it)
        })
    }

    override fun onUserItemClick(githubUser: GithubUser) {
        val directions = UsersListFragmentDirections.actionUsersListFragmentToSecondFragment(githubUser.login)
        findNavController().navigate(directions)
    }
}