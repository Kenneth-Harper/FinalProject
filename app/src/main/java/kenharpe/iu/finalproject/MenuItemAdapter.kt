package kenharpe.iu.finalproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kenharpe.iu.finalproject.databinding.FoodItemBinding
import kenharpe.iu.finalproject.model.Food

class MenuItemAdapter (private val subtractClickListener: (item: Food) -> Unit, private val addClickListener: (item: Food) -> Unit)
    : ListAdapter<Food, MenuItemAdapter.ItemViewHolder>(MenuDiffItemCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ItemViewHolder = ItemViewHolder.inflateFrom(parent)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int)
    {
        val item = getItem(position)
        holder.bind(item, subtractClickListener, addClickListener)
    }

    class ItemViewHolder(private val binding: FoodItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        companion object{
            fun inflateFrom(parent: ViewGroup) : ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FoodItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }
        }

        fun bind(item: Food, subtractClickListener: (item: Food) -> Unit, addClickListener: (item: Food) -> Unit)
        {
            binding.textviewFoodItemName.text = item.name
            binding.textviewFoodItemPrice.text = String.format("$%.2f", item.price)
            binding.textviewFoodItemCount.text = item.count.toString()
            binding.buttonSubtractFromOrder.setOnClickListener {
                var newValue = binding.textviewFoodItemCount.text.toString().toInt() - 1
                if (newValue < 0)
                {
                    newValue = 0
                }
                binding.textviewFoodItemCount.text = newValue.toString()
                subtractClickListener(item)
            }
            binding.buttonAddToOrder.setOnClickListener {
                var newValue = binding.textviewFoodItemCount.text.toString().toInt() + 1
                binding.textviewFoodItemCount.text = newValue.toString()
                subtractClickListener(item)
            }
        }
    }
}

class MenuDiffItemCallback : DiffUtil.ItemCallback<Food>()
{
    override fun areItemsTheSame(oldItem: Food, newItem: Food) = (oldItem.name == newItem.name)
    override fun areContentsTheSame(oldItem: Food, newItem: Food) = (oldItem == newItem)
}