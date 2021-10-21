package cz.palda97.repoviewer.view.userdetail

import cz.palda97.repoviewer.R
import cz.palda97.repoviewer.databinding.ListItemTwoLinesBinding
import cz.palda97.repoviewer.model.entity.Repository
import cz.palda97.repoviewer.view.BasicRecyclerAdapter

class RepositoryAdapter(
    private val onClick: (Repository) -> Unit
) :
    BasicRecyclerAdapter<Repository, ListItemTwoLinesBinding>(R.layout.list_item_two_lines) {

    override fun areItemsSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }

    override fun areContentsSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem.name == newItem.name && oldItem.description == newItem.description
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder<ListItemTwoLinesBinding>,
        position: Int,
        item: Repository,
        list: List<Repository>
    ) {
        holder.binding.upperText = item.name
        holder.binding.bottomText = item.description
        holder.binding.executePendingBindings()
        holder.itemView.setOnClickListener {
            onClick(list[holder.adapterPosition])
        }
    }
}