package com.example.githubrepo.presentation.datasource.repoCommits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepo.R
import com.example.githubrepo.databinding.FragmentRepoCommitsBinding
import com.example.githubrepo.presentation.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepoCommitsFragment : Fragment() {

    private val repoCommitsViewModel: UserViewModel by viewModel()
    private lateinit var itemViewer: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentRepoCommitsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_repo_commits, container, false)
        binding.repoCommitsViewModel = repoCommitsViewModel
        itemViewer = binding.root.findViewById(R.id.commitItemViewer)

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val commitsListAdapter = RepoCommitsListAdapter(arrayListOf())
        itemViewer.layoutManager = LinearLayoutManager(context)
        itemViewer.adapter = commitsListAdapter

        arguments?.let {
            val owner: String = RepoCommitsFragmentArgs.fromBundle(it).owner
            val repo: String = RepoCommitsFragmentArgs.fromBundle(it).repo
            repoCommitsViewModel.getRepoCommits(owner, repo)
                .observe(viewLifecycleOwner, Observer { commits ->
                    if (commits.isNotEmpty()) {
                        commitsListAdapter.addAll(commits)
                    }
                })
        }
    }
}