package com.example.githubrepo.presentation.datasource.userRepos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepo.R
import com.example.githubrepo.data.model.UserRepos
import com.example.githubrepo.databinding.FragmentUserReposBinding
import com.example.githubrepo.presentation.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserReposFragment : Fragment(), UserReposListAdapter.OnRepoSelected {

    private val userViewModel: UserViewModel by viewModel()
    private lateinit var repoItemViewer: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentUserReposBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_repos, container, false)
        binding.userRepoViewModel = userViewModel
        repoItemViewer = binding.root.findViewById(R.id.repoItemViewer)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val repoListAdapter = UserReposListAdapter(arrayListOf(), this)
        repoItemViewer.layoutManager = LinearLayoutManager(context)
        repoItemViewer.adapter = repoListAdapter

        arguments?.let {
            val username: String = UserReposFragmentArgs.fromBundle(it) .username
            userViewModel.getUserRepos(username).observe(viewLifecycleOwner, Observer {
                repoListAdapter.addAll(it)
            })
        }
    }

    override fun onRepositorySelected(userRepos: UserRepos) {
        val directions = UserReposFragmentDirections.actionUserRepoFragmentToRepoCommitFragment(userRepos.owner.login, userRepos.name)
        findNavController().navigate(directions)
    }
}