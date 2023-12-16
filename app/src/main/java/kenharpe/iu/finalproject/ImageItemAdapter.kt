package kenharpe.iu.finalproject

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kenharpe.iu.finalproject.databinding.FoodItemBinding
import kenharpe.iu.finalproject.databinding.RestaurantImageItemBinding

class ImageItemAdapter ()
    : ListAdapter<String, ImageItemAdapter.ItemViewHolder>(ImageDiffItemCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ItemViewHolder = ItemViewHolder.inflateFrom(parent)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int)
    {
        val item = getItem(position)
        holder.bind(item)
    }

    class ItemViewHolder(private val binding: RestaurantImageItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        companion object{
            fun inflateFrom(parent: ViewGroup) : ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RestaurantImageItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }
        }

        fun bind(item: String)
        {
            val imageContainer = binding.imageviewRestaurantImage
            val imageUrl = Uri.parse(item)
            Glide.with(imageContainer.context).load(imageUrl).into(imageContainer)
        }
    }
}

class ImageDiffItemCallback : DiffUtil.ItemCallback<String>()
{
    override fun areItemsTheSame(oldItem: String, newItem: String) = (oldItem == newItem)
    override fun areContentsTheSame(oldItem: String, newItem: String) = (oldItem == newItem)
}