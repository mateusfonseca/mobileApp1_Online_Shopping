package ie.dorset.student_24088.project.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ie.dorset.student_24088.project.MainActivity
import ie.dorset.student_24088.project.R
import ie.dorset.student_24088.project.adapter.ItemListAdapter
import ie.dorset.student_24088.project.adapter.ItemListener
import ie.dorset.student_24088.project.databinding.FragmentProductListBinding
import ie.dorset.student_24088.project.model.Product
import ie.dorset.student_24088.project.viewmodel.ProductViewModel

class ProductListFragment : Fragment() {

    private val viewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProductListBinding.inflate(inflater)

        val categoryName = arguments?.getString("NAME")!!
        viewModel.getProductsList(categoryName)

        (activity as MainActivity).supportActionBar?.apply {
            title = resources.getString(R.string.app_name)
            subtitle = categoryName.replaceFirstChar { it.uppercaseChar() }
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recyclerView.adapter = ItemListAdapter("ProductListFragment", ItemListener { data ->
            viewModel.onProductClicked(data as Product)
            findNavController()
                .navigate(R.id.action_productListFragment_to_productFragment)
        })

        // Inflate the layout for this fragment
        return binding.root
    }
}