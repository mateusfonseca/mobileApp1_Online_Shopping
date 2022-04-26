package ie.dorset.student_24088.project.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ie.dorset.student_24088.project.R
import ie.dorset.student_24088.project.databinding.RecyclerViewItemBinding
import ie.dorset.student_24088.project.model.Data

class ItemListAdapter(private val callerTag: String, private val clickListener: ItemListener) :
    ListAdapter<Data, ItemListAdapter.ItemViewHolder>(DiffCallback) {

    class ItemViewHolder(
        var binding: RecyclerViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: ItemListener, item: Data) {
            binding.item = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Data>() {

        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.lastModified == newItem.lastModified
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(
            RecyclerViewItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        Log.d("Adapt", item.toString())
        holder.bind(clickListener, item)

        holder.binding.itemStub.viewStub?.layoutResource =
            when (callerTag) {
                "CategoryListFragment" -> R.layout.recycler_view_item_category_list
                "ProductListFragment" -> R.layout.recycler_view_item_product_list
                "CartFragment" -> R.layout.recycler_view_item_cart_product_list
                "OrderListFragment" -> R.layout.recycler_view_item_order_list
                else -> R.layout.recycler_view_item_cart_product_list
            }

        holder.binding.itemStub.viewStub?.inflate()
    }
}

class ItemListener(val clickListener: (item: Data) -> Unit) {
    fun onClick(item: Data) {
        return clickListener(item)
    }
}