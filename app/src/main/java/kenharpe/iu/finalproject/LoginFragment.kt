package kenharpe.iu.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import kenharpe.iu.finalproject.databinding.FragmentLoginBinding

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    val TAG = "LogInFragment"
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GlobalViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.edittextName.addTextChangedListener{
            text -> viewModel.updateName(text.toString())
        }

        binding.edittextEmail.addTextChangedListener {
            text -> viewModel.updateEmail(text.toString().trim())
        }

        binding.edittextPassword.addTextChangedListener {
            text -> viewModel.updatePassword(text.toString())
        }

        binding.buttonSignIn.setOnClickListener {
            viewModel.trySignIn()
        }

        viewModel.goToHomeScreen.observe(viewLifecycleOwner, Observer {
            if (it)
            {
                view.findNavController().navigate(R.id.homeFragment)
            }
        })

        return view
    }
}