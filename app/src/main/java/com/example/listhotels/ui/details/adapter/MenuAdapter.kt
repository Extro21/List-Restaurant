package com.example.listhotels.ui.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.listhotels.R
import com.example.listhotels.databinding.MenuItemBinding
import com.example.listhotels.domain.models.MenuItem

class MenuAdapter :
    ListAdapter<MenuItem, MenuAdapter.MenuHolder>(ItemComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        return MenuHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MenuHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = MenuItemBinding.bind(view)

        fun bind(menu: MenuItem) = with(binding) {
            name.text = menu.name
        }

        companion object {

            fun create(parent: ViewGroup): MenuHolder {
                return MenuHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.menu_item, parent, false)
                )
            }

        }
    }

    class ItemComparator : DiffUtil.ItemCallback<MenuItem>() {
        override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
            return oldItem == newItem
        }
    }
}