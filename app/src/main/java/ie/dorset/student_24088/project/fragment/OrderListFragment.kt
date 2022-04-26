package ie.dorset.student_24088.project.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ie.dorset.student_24088.project.R
import ie.dorset.student_24088.project.adapter.ItemListAdapter
import ie.dorset.student_24088.project.adapter.ItemListener
import ie.dorset.student_24088.project.databinding.FragmentOrderListBinding
import ie.dorset.student_24088.project.model.Cart
import ie.dorset.student_24088.project.viewmodel.OrderViewModel

class OrderListFragment : Fragment() {
    private val viewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOrderListBinding.inflate(inflater)

        viewModel.getOrdersList()

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recyclerView.adapter = ItemListAdapter("OrderListFragment", ItemListener { data ->
            val order = viewModel.onOrderClicked(data as Cart)
            val bundle = bundleOf("order" to order)
            findNavController()
                .navigate(R.id.action_viewPagerFragment_to_orderFragment, bundle)
        })

        // Inflate the layout for this fragment
        return binding.root
    }
}