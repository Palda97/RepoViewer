package cz.palda97.repoviewer.view.repodetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cz.palda97.repoviewer.R
import cz.palda97.repoviewer.databinding.ListItemOneLineBinding
import cz.palda97.repoviewer.model.entity.Branch

/**
 * RecyclerView adapter for the branch list.
 */
class BranchRecyclerAdapter() : RecyclerView.Adapter<BranchRecyclerAdapter.BranchViewHolder>() {

    private var branchList: List<Branch> = emptyList()

    class BranchViewHolder(val binding: ListItemOneLineBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun updateBranchList(newBranchList: List<Branch>) {
        if (branchList.isEmpty()) {
            branchList = newBranchList
            notifyItemRangeInserted(0, newBranchList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

                override fun getOldListSize(): Int {
                    return branchList.size
                }

                override fun getNewListSize(): Int {
                    return newBranchList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldItem = branchList[oldItemPosition]
                    val newItem = newBranchList[newItemPosition]
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val oldItem = branchList[oldItemPosition]
                    val newItem = newBranchList[newItemPosition]
                    return oldItem.name == newItem.name
                }
            })
            branchList = newBranchList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val binding = DataBindingUtil.inflate<ListItemOneLineBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_one_line,
            parent,
            false
        )
        return BranchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return branchList.size
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        val branch = branchList[position]
        holder.binding.text = branch.name
        holder.binding.executePendingBindings()
    }
}