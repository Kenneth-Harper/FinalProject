package kenharpe.iu.finalproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kenharpe.iu.finalproject.databinding.RestaurantItemBinding
import kenharpe.iu.finalproject.model.Restaurant


class RestaurantItemAdapter (private val titleClickListener: (restaurantId: String) -> Unit)
    : ListAdapter<Restaurant, RestaurantItemAdapter.ItemViewHolder>(RestaurantDiffItemCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ItemViewHolder = ItemViewHolder.inflateFrom(parent)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int)
    {
        val item = getItem(position)
        holder.bind(item, titleClickListener)
    }

    class ItemViewHolder(private val binding: RestaurantItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        companion object{
            fun inflateFrom(parent: ViewGroup) : ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RestaurantItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }
        }

        fun bind(item: Restaurant, titleClickListener: (restaurantId: String) -> Unit) {
            binding.restaurant = item
            binding.textviewRestaurantItemName.text = item.name
            binding.cardviewRestaurant.setOnClickListener {titleClickListener(item.id)}
        }
    }
}

class RestaurantDiffItemCallback : DiffUtil.ItemCallback<Restaurant>()
{
    override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant) = (oldItem.id == newItem.id)
    override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant) = (oldItem == newItem)
}