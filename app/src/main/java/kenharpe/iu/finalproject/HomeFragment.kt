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

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GlobalViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentHomeBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        val favoritesAdapter = RestaurantItemAdapter({reminderId -> viewModel.onRestaurantClicked(reminderId)})
        binding.recyclerviewFavoriteRestaurants.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerviewFavoriteRestaurants.adapter = favoritesAdapter
        viewModel.favoriteRestaurants.observe(viewLifecycleOwner, Observer {
            it?.let {
                favoritesAdapter.submitList(it)
            }
        })

        val restaurantsAdapter = RestaurantItemAdapter({reminderId -> viewModel.onRestaurantClicked(reminderId)})
        binding.recyclerviewAllRestaurants.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerviewAllRestaurants.adapter = restaurantsAdapter
        viewModel.allRestaurants.observe(viewLifecycleOwner, Observer {
            it?.let {
                restaurantsAdapter.submitList(it)
            }
        })

        viewModel.currentlySelectedRestaurant.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                view.findNavController().navigate(R.id.restaurantFragment)
            }
        })

        return view
    }
}