package cz.palda97.repoviewer.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView adapter for the branch list.
 */
abstract class BasicRecyclerAdapter<Item : Any, V : ViewDataBinding>(private val layout: Int) :
    RecyclerView.Adapter<BasicRecyclerAdapter.ItemViewHolder<V>>() {

    abstract fun areItemsSame(oldItem: Item, newItem: Item): Boolean
    abstract fun areContentsSame(oldItem: Item, newItem: Item): Boolean
    abstract fun onBindViewHolder(holder: ItemViewHolder<V>, position: Int, item: Item, list: List<Item>)

    private var itemList: List<Item> = emptyList()

    class ItemViewHolder<B : ViewDataBinding>(val binding: B) :
        RecyclerView.ViewHolder(binding.root)

    fun updateItemList(newItemList: List<Item>) {
        if (itemList.isEmpty()) {
            itemList = newItemList
            notifyItemRangeInserted(0, newItemList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

                override fun getOldListSize(): Int {
                    return itemList.size
                }

                override fun getNewListSize(): Int {
                    return newItemList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldItem = itemList[oldItemPosition]
                    val newItem = newItemList[newItemPosition]
                    return areItemsSame(oldItem, newItem)
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val oldItem = itemList[oldItemPosition]
                    val newItem = newItemList[newItemPosition]
                    return areContentsSame(oldItem, newItem)
                }
            })
            itemList = newItemList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<V> {
        val binding = DataBindingUtil.inflate<V>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder<V>, position: Int) {
        val item = itemList[position]
        onBindViewHolder(holder, position, item, itemList)
    }
}