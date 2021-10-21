package cz.palda97.repoviewer.view.repodetail

import cz.palda97.repoviewer.R
import cz.palda97.repoviewer.databinding.ListItemOneLineBinding
import cz.palda97.repoviewer.model.entity.Branch
import cz.palda97.repoviewer.view.BasicRecyclerAdapter

class BranchAdapter :
    BasicRecyclerAdapter<Branch, ListItemOneLineBinding>(R.layout.list_item_one_line) {

    override fun areItemsSame(oldItem: Branch, newItem: Branch): Boolean {
        return oldItem == newItem
    }

    override fun areContentsSame(oldItem: Branch, newItem: Branch): Boolean {
        return oldItem.name == newItem.name
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder<ListItemOneLineBinding>,
        position: Int,
        item: Branch,
        list: List<Branch>
    ) {
        holder.binding.text = item.name
        holder.binding.executePendingBindings()
    }
}