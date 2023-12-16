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
import kenharpe.iu.finalproject.databinding.FragmentCheckOutBinding
import kenharpe.iu.finalproject.databinding.FragmentHomeBinding


class CheckOutFragment : Fragment()
{
    private var _binding: FragmentCheckOutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GlobalViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentCheckOutBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        val menuItemAdapter = MenuItemAdapter({}, {})
        binding.recyclerviewOrderItems.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerviewOrderItems.adapter = menuItemAdapter
        viewModel.foodChoices.observe(viewLifecycleOwner, Observer {
            it?.keys?.toList().let {
                menuItemAdapter.submitList(it)
            }
        })

        binding.buttonModifyOrder.setOnClickListener {
            view.findNavController().navigate(R.id.restaurantFragment)
        }
        return view
    }
}