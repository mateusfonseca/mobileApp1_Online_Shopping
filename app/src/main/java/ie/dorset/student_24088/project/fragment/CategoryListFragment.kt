package ie.dorset.student_24088.project.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ie.dorset.student_24088.project.R
import ie.dorset.student_24088.project.adapter.ItemListAdapter
import ie.dorset.student_24088.project.adapter.ItemListener
import ie.dorset.student_24088.project.databinding.FragmentCategoryListBinding
import ie.dorset.student_24088.project.model.Category
import ie.dorset.student_24088.project.viewmodel.CategoryViewModel

class CategoryListFragment : Fragment() {

    private val viewModel: CategoryViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCategoryListBinding.inflate(inflater)

        viewModel.getCategoriesList()

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recyclerView.adapter =
            ItemListAdapter("CategoryListFragment", ItemListener { data ->
                val categoryName = viewModel.onCategoryClicked(data as Category)
                val bundle = bundleOf("NAME" to categoryName)
                findNavController().navigate(
                    R.id.action_viewPagerFragment_to_productListFragment,
                    bundle
                )
            })

        // Inflate the layout for this fragment
        return binding.root
    }
}