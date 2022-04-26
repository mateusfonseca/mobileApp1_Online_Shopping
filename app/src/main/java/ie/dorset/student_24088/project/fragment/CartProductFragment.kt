package ie.dorset.student_24088.project.fragment

import android.icu.text.NumberFormat
import android.icu.text.NumberFormat.CURRENCYSTYLE
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ie.dorset.student_24088.project.MainActivity
import ie.dorset.student_24088.project.R
import ie.dorset.student_24088.project.databinding.FragmentCartProductBinding
import ie.dorset.student_24088.project.viewmodel.CartViewModel

class CartProductFragment : Fragment() {

    private val viewModel: CartViewModel by activityViewModels()
    private var _binding: FragmentCartProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartProductBinding.inflate(inflater, container, false)

        (activity as MainActivity).supportActionBar?.apply {
            title = resources.getString(R.string.app_name)
            subtitle = viewModel.cartProductDetails.value?.let {
                it.category.replaceFirstChar { char -> char.uppercaseChar() }
            }
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel

        // Inflate the layout for this fragment
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            var itemNumber = viewModel!!.getQuantityById(viewModel!!.cartProductDetails.value!!.id)
            var itemPrice =
                viewModel!!.getProductPriceQuantityByIdAsDouble(viewModel!!.cartProductDetails.value!!.id)
            val singlePrice = viewModel!!.cartProductDetails.value!!.price

            buttonRemove.setOnClickListener {
                if (itemNumber > 1) {
                    numberItemsSelected.text = (--itemNumber).toString()
                    itemPrice -= singlePrice
                    priceItemNumber.text = NumberFormat.getInstance(CURRENCYSTYLE).format(itemPrice)
                }
                if (itemNumber == 1) {
                    buttonRemove.isClickable = false
                }
            }

            buttonAdd.setOnClickListener {
                numberItemsSelected.text = (++itemNumber).toString()
                itemPrice += singlePrice
                priceItemNumber.text = NumberFormat.getInstance(CURRENCYSTYLE).format(itemPrice)
                if (itemNumber > 1) {
                    buttonRemove.isClickable = true
                    buttonUpdateCart.isClickable = true
                }
            }

            buttonUpdateCart.setOnClickListener {
                binding.viewModel?.updateCartDetails()

                Snackbar.make(
                    view, "Cart updated!",
                    BaseTransientBottomBar.LENGTH_LONG
                ).show()
            }

            buttonDeleteItem.setOnClickListener {
                binding.viewModel?.removeCartItem()

                Snackbar.make(
                    view, "Item removed from cart!",
                    BaseTransientBottomBar.LENGTH_LONG
                ).show()
            }
        }
    }
}