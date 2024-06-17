package com.example.listhotels.ui.listRestaurant.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.listhotels.domain.models.RestaurantItem
import com.example.listhotels.R
import com.example.listhotels.databinding.RestaurantItemBinding

class RestaurantAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantHolder>() {

    fun interface OnItemClickListener {
        fun onItemButtonClick(hostelId: Int)
    }

    private var listRestaurant = ArrayList<RestaurantItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.restaurant_item, parent, false)
        return RestaurantHolder(view)
    }

    override fun getItemCount(): Int = listRestaurant.size

    override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
        holder.bind(listRestaurant[position])

        holder.button.setOnClickListener {
            listener.onItemButtonClick(listRestaurant[position].id)
        }
    }

    class RestaurantHolder(private val item: View) : RecyclerView.ViewHolder(item) {
        private val binding = RestaurantItemBinding.bind(item)

        fun bind(restaurantItem: RestaurantItem) = with(binding) {
            nameRestaurant.text = restaurantItem.name
            addressRestaurant.text = restaurantItem.address
            starsRestaurant.text = restaurantItem.rating.toString()
            distanceRestaurant.text = item.context.getString(
                R.string.from_center_format,
                restaurantItem.distance.toString(), item.context.getString(R.string.from_center)
            )

            val count = restaurantItem.freeTables
            val ending = getEnding(count)
            suitesAvailabilityRestaurant.text =
                item.context.getString(R.string.rooms_available, count, ending)
        }

        val button = binding.btDetailsInfo

        private fun getEnding(number: Int): String {
            return if (number <= 1) {
                item.context.getString(R.string.table)
            } else {
                item.context.getString(R.string.tables)
            }
        }
    }


    fun updateData(newData: List<RestaurantItem>) {
        val diffCallback = DiffCallback(listRestaurant, newData)
        val diffRisult = DiffUtil.calculateDiff(diffCallback)
        listRestaurant.clear()
        listRestaurant.addAll(newData)
        diffRisult.dispatchUpdatesTo(this)
    }

}

private class DiffCallback(
    private val oldList: List<RestaurantItem>,
    private val newList: List<RestaurantItem>,
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}