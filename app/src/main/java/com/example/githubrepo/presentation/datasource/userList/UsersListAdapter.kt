package com.example.githubrepo.presentation.datasource.userList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubrepo.R
import com.example.githubrepo.data.model.GithubUserModel
import kotlinx.android.synthetic.main.users_list_item.view.*

class UsersListAdapter(private val onUsersSelected: OnUsersSelected) :
    PagedListAdapter<GithubUserModel, UsersListAdapter.UsersListViewHolder>(usersDiffCallback) {
    lateinit var context: Context

    interface OnUsersSelected {
        fun onUserItemClick(githubUserModel: GithubUserModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.users_list_item, parent, false)
        return UsersListViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {
        val githubUserModel = getItem(position)
        githubUserModel?.let { holder.bind(it) }
    }

    inner class UsersListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(githubUserModel: GithubUserModel) {
            with(itemView) {
                txtName.text = githubUserModel.login
                txtHomeUrl.text = githubUserModel.htmlUrl
                githubUserModel.avatarUrl.let {
                    Glide.with(context)
                        .load(it)
                        .apply(RequestOptions.circleCropTransform())
                        .into(imgAvatar)
                }

                setOnClickListener {
                    onUsersSelected.onUserItemClick(githubUserModel)
                }
            }
        }
    }

    companion object {
        val usersDiffCallback = object : DiffUtil.ItemCallback<GithubUserModel>() {
            override fun areItemsTheSame(
                oldItem: GithubUserModel,
                newItem: GithubUserModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: GithubUserModel,
                newItem: GithubUserModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}