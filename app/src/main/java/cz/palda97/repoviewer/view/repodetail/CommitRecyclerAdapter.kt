package cz.palda97.repoviewer.view.repodetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cz.palda97.repoviewer.R
import cz.palda97.repoviewer.databinding.ListItemOneLineBinding
import cz.palda97.repoviewer.model.entity.GitCommit

class CommitRecyclerAdapter() : RecyclerView.Adapter<CommitRecyclerAdapter.CommitViewHolder>() {

    private var commitList: List<GitCommit> = emptyList()

    class CommitViewHolder(val binding: ListItemOneLineBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun updateCommitList(newCommitList: List<GitCommit>) {
        if (commitList.isEmpty()) {
            commitList = newCommitList
            notifyItemRangeInserted(0, newCommitList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

                override fun getOldListSize(): Int {
                    return commitList.size
                }

                override fun getNewListSize(): Int {
                    return newCommitList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldItem = commitList[oldItemPosition]
                    val newItem = newCommitList[newItemPosition]
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val oldItem = commitList[oldItemPosition]
                    val newItem = newCommitList[newItemPosition]
                    return oldItem.message == newItem.message
                }
            })
            commitList = newCommitList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
        val binding = DataBindingUtil.inflate<ListItemOneLineBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_one_line,
            parent,
            false
        )
        return CommitViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return commitList.size
    }

    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
        val commit = commitList[position]
        holder.binding.text = commit.message
        holder.binding.executePendingBindings()
    }
}