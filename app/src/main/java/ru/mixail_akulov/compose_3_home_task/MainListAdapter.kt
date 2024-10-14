package ru.mixail_akulov.compose_3_home_task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.mixail_akulov.compose_3_home_task.databinding.ItemBinding

class MainListAdapter(
    private val onDeletePressed: (item: Item) -> Unit
) : ListAdapter<Item, MainListAdapter.ViewHolder>(MainItemCallback), View.OnClickListener {

    override fun onClick(v: View?) {
        if (v?.id == R.id.deleteImageView) {
            onDeletePressed(v.tag as Item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBinding.inflate(inflater, parent, false)
        binding.deleteImageView.setOnClickListener(this)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder(
        private val binding: ItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) = with(binding) {
            titleTextView.text = item.title
            deleteImageView.isVisible = !item.isDeleting
            deleteImageView.tag = item
            progressBar.alpha = if (item.isDeleting) 1.0f else 0.0f
        }
    }

    object MainItemCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

}