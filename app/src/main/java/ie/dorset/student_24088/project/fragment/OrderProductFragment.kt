package ie.dorset.student_24088.project.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ie.dorset.student_24088.project.MainActivity
import ie.dorset.student_24088.project.R
import ie.dorset.student_24088.project.databinding.FragmentOrderProductBinding
import ie.dorset.student_24088.project.viewmodel.OrderViewModel
import ie.dorset.student_24088.project.viewmodel.ProductViewModel

class OrderProductFragment : Fragment() {

    private val viewModel: OrderViewModel by activityViewModels()
    private val productViewModel: ProductViewModel by activityViewModels()

    private var _binding: FragmentOrderProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderProductBinding.inflate(inflater, container, false)

        (activity as MainActivity).supportActionBar?.apply {
            title = resources.getString(R.string.app_name)
            subtitle = viewModel.orderProductDetails.value?.let {
                it.category.replaceFirstChar { char -> char.uppercaseChar() }
            }
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel

        productViewModel.getProductsList(viewModel.orderProductDetails.value!!.id)

        binding.buttonBuyAgain.setOnClickListener {
            findNavController().navigate(R.id.action_orderProductFragment_to_productFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}