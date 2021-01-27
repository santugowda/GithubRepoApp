package com.example.githubrepo.presentation.datasource.userRepos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubrepo.R
import com.example.githubrepo.data.model.UserRepos
import kotlinx.android.synthetic.main.users_repo_item.view.*

class UserReposListAdapter(
    private val userRepos: ArrayList<UserRepos>,
    private val clickListener: OnRepoSelected
) : RecyclerView.Adapter<UserReposListAdapter.RepositoryItemsViewHolder>() {

    interface OnRepoSelected {
        fun onRepositorySelected(userRepos: UserRepos)
    }

    fun addAll(userRepositoryList : List<UserRepos>) {
        val size = userRepos.size
        userRepos.clear()
        userRepos.addAll(userRepositoryList);
        notifyDataSetChanged()
    }

    fun reset() {
        userRepos.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryItemsViewHolder {
        return RepositoryItemsViewHolder(
            (LayoutInflater.from(parent.context)
                .inflate(R.layout.users_repo_item, parent, false))
        )
    }

    override fun onBindViewHolder(holder: RepositoryItemsViewHolder, position: Int) {
        holder.bind(userRepos[position])
    }

    override fun getItemCount(): Int {
        return userRepos.size
    }

    inner class RepositoryItemsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(userRepo: UserRepos) {
            with(itemView) {
                repoName.text = userRepo.name
                repoDetails.text = userRepo.description
                userRepo.owner.avatarUrl.let {
                    Glide.with(context)
                        .load(it).apply(RequestOptions().circleCrop())
                        .into(userAvatar)
                }

                setOnClickListener {
                    clickListener.onRepositorySelected(userRepo)
                }
            }
        }
    }
}