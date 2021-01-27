package com.example.githubrepo.presentation.datasource.repoCommits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepo.R
import com.example.githubrepo.data.model.CommitsModel
import kotlinx.android.synthetic.main.repo_commit_item.view.*

class RepoCommitsListAdapter(private val commitsModel: ArrayList<CommitsModel>) :
    RecyclerView.Adapter<RepoCommitsListAdapter.CommitsItemsViewHolder>() {

    fun addAll(commitModelList: List<CommitsModel>) {
        val size = commitsModel.size
        commitsModel.clear()
        commitsModel.addAll(commitModelList);
        notifyDataSetChanged()
    }

    fun reset() {
        commitsModel.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitsItemsViewHolder {
        return CommitsItemsViewHolder(
            (LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_commit_item, parent, false))
        )
    }

    override fun onBindViewHolder(holder: CommitsItemsViewHolder, position: Int) {
        holder.bind(commitsModel[position])
    }

    override fun getItemCount(): Int {
        return commitsModel.size
    }

    inner class CommitsItemsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(commitModel: CommitsModel) {
            with(itemView) {
                author.text =
                    context.getString(R.string.repo_author).plus(commitModel.commit.author.name)
                commitHash.text = context.getString(R.string.commit_hash).plus(commitModel.sha)
                commitMessage.text =
                    context.getString(R.string.commit_message).plus(commitModel.commit.message)
            }
        }
    }
}