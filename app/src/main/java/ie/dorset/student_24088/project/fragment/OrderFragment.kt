package ie.dorset.student_24088.project.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ie.dorset.student_24088.project.MainActivity
import ie.dorset.student_24088.project.R
import ie.dorset.student_24088.project.adapter.ItemListAdapter
import ie.dorset.student_24088.project.adapter.ItemListener
import ie.dorset.student_24088.project.databinding.FragmentOrderBinding
import ie.dorset.student_24088.project.model.Cart
import ie.dorset.student_24088.project.model.Product
import ie.dorset.student_24088.project.viewmodel.OrderViewModel

class OrderFragment : Fragment() {
    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val order = arguments?.getParcelable<Cart>("order")!!
        viewModel.onCheckOut(order)
        viewModel.getOrderProductsList(order)

        (activity as MainActivity).supportActionBar?.apply {
            title = resources.getString(R.string.app_name)
            subtitle = "Order #${viewModel.order.value?.id}"
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recyclerView.adapter = ItemListAdapter("OrderFragment", ItemListener { data ->
            viewModel.onProductClicked(data as Product)
            findNavController().navigate(R.id.action_orderFragment_to_orderProductFragment)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}