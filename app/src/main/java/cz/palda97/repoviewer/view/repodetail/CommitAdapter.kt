package cz.palda97.repoviewer.view.repodetail

import cz.palda97.repoviewer.R
import cz.palda97.repoviewer.databinding.ListItemOneLineBinding
import cz.palda97.repoviewer.model.entity.GitCommit
import cz.palda97.repoviewer.view.BasicRecyclerAdapter

class CommitAdapter :
    BasicRecyclerAdapter<GitCommit, ListItemOneLineBinding>(R.layout.list_item_one_line) {

    override fun areItemsSame(oldItem: GitCommit, newItem: GitCommit): Boolean {
        return oldItem == newItem
    }

    override fun areContentsSame(oldItem: GitCommit, newItem: GitCommit): Boolean {
        return oldItem.message == newItem.message
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder<ListItemOneLineBinding>,
        position: Int,
        item: GitCommit,
        list: List<GitCommit>
    ) {
        holder.binding.text = item.message
        holder.binding.executePendingBindings()
    }
}