package cz.palda97.repoviewer.view.userdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cz.palda97.repoviewer.R
import cz.palda97.repoviewer.databinding.ListItemTwoLinesBinding
import cz.palda97.repoviewer.model.entity.Repository

/**
 * RecyclerView adapter for the repository list.
 */
class RepositoryRecyclerAdapter(
    private val onClick: (Repository) -> Unit
) : RecyclerView.Adapter<RepositoryRecyclerAdapter.RepositoryViewHolder>() {

    private var repositoryList: List<Repository> = emptyList()

    class RepositoryViewHolder(val binding: ListItemTwoLinesBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun updateRepositoryList(newRepositoryList: List<Repository>) {
        if (repositoryList.isEmpty()) {
            repositoryList = newRepositoryList
            notifyItemRangeInserted(0, newRepositoryList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

                override fun getOldListSize(): Int {
                    return repositoryList.size
                }

                override fun getNewListSize(): Int {
                    return newRepositoryList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldItem = repositoryList[oldItemPosition]
                    val newItem = newRepositoryList[newItemPosition]
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val oldItem = repositoryList[oldItemPosition]
                    val newItem = newRepositoryList[newItemPosition]
                    return oldItem.name == newItem.name && oldItem.description == newItem.description
                }
            })
            repositoryList = newRepositoryList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding = DataBindingUtil.inflate<ListItemTwoLinesBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_two_lines,
            parent,
            false
        )
        return RepositoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return repositoryList.size
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val repository = repositoryList[position]
        holder.binding.upperText = repository.name
        holder.binding.bottomText = repository.description
        holder.binding.executePendingBindings()
        holder.itemView.setOnClickListener {
            onClick(repositoryList[holder.adapterPosition])
        }
    }
}