package ie.dorset.student_24088.project.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import ie.dorset.student_24088.project.MainActivity
import ie.dorset.student_24088.project.R
import ie.dorset.student_24088.project.databinding.FragmentProductBinding
import ie.dorset.student_24088.project.viewmodel.ProductViewModel

class ProductFragment : Fragment() {

    private val viewModel: ProductViewModel by activityViewModels()
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)

        (activity as MainActivity).supportActionBar?.apply {
            title = resources.getString(R.string.app_name)
            subtitle = viewModel.product.value?.let {
                it.category.replaceFirstChar { char -> char.uppercaseChar() }
            }
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            var itemNumber = numberItemsSelected.text.toString().toInt()

            buttonRemove.setOnClickListener {
                if (itemNumber > 0)
                    numberItemsSelected.text = (--itemNumber).toString()
                if (itemNumber == 0) {
                    buttonRemove.isClickable = false
                    buttonAddToCart.isClickable = false
                }
            }

            buttonAdd.setOnClickListener {
                numberItemsSelected.text = (++itemNumber).toString()
                if (itemNumber > 0) {
                    buttonRemove.isClickable = true
                    buttonAddToCart.isClickable = true
                }
            }

            buttonAddToCart.setOnClickListener {
                if (itemNumber > 0)
                    Snackbar.make(view, "$itemNumber item(s) added to your cart!", LENGTH_LONG)
                        .show()
                if (itemNumber == 0)
                    buttonAddToCart.isClickable = false
            }
        }
    }
}