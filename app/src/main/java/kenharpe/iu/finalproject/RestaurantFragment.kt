package kenharpe.iu.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kenharpe.iu.finalproject.databinding.FragmentHomeBinding
import kenharpe.iu.finalproject.databinding.FragmentRestaurantBinding
import kenharpe.iu.finalproject.model.Food


class RestaurantFragment : Fragment()
{
    private var _binding: FragmentRestaurantBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GlobalViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        _binding =  FragmentRestaurantBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        val imageAdapter = ImageItemAdapter()
        binding.recyclerviewRestaurantPhotos.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerviewRestaurantPhotos.adapter = imageAdapter
        viewModel.restaurantImages.observe(viewLifecycleOwner, Observer {
            it?.let {
                imageAdapter.submitList(it)
            }
        })

        val menuItemAdapter = MenuItemAdapter({ viewModel.subtractFoodItem(it) }, { viewModel.addFoodItem(it) })
        binding.recyclerviewMenuItems.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerviewMenuItems.adapter = menuItemAdapter
        viewModel.restaurantMenuOptions.observe(viewLifecycleOwner, Observer {
            it?.let {
                menuItemAdapter.submitList(it)
            }
        })

        binding.buttonRestaurantCheckOut.setOnClickListener {
            view.findNavController().navigate(R.id.checkOutFragment)
        }
        return view

    }
}