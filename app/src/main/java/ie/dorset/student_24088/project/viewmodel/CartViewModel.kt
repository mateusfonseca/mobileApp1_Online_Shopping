package ie.dorset.student_24088.project.viewmodel

import android.icu.text.NumberFormat
import android.icu.text.NumberFormat.CURRENCYSTYLE
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ie.dorset.student_24088.project.model.Cart
import ie.dorset.student_24088.project.model.CartProduct
import ie.dorset.student_24088.project.model.Product
import ie.dorset.student_24088.project.model.Session.userId
import ie.dorset.student_24088.project.network.OnlineShoppingApi
import ie.dorset.student_24088.project.network.OnlineShoppingApiStatus
import kotlinx.coroutines.launch
import java.time.LocalDate

class CartViewModel : ViewModel() {

    private val _status = MutableLiveData<OnlineShoppingApiStatus>()
    val status: LiveData<OnlineShoppingApiStatus> = _status

    private val _cart = MutableLiveData<Cart>()
    val cart: LiveData<Cart> = _cart

    private val _cartProducts = MutableLiveData<List<CartProduct>>()
    private val cartProducts: LiveData<List<CartProduct>> = _cartProducts

    private val _cartProductsDetails = MutableLiveData<List<Product>>()
    val cartProductsDetails: LiveData<List<Product>> = _cartProductsDetails

    private val _cartProductDetails = MutableLiveData<Product>()
    val cartProductDetails: LiveData<Product> = _cartProductDetails

    private val _orderedQuantities = MutableLiveData<List<Int>>()
    private val orderedQuantities: LiveData<List<Int>> = _orderedQuantities

    private val _orderedPrices = MutableLiveData<List<Double>>()
    private val orderedPrices: LiveData<List<Double>> = _orderedPrices

    private val _totalAmount = MutableLiveData<Double>()
    private val totalAmount: LiveData<Double> = _totalAmount

    private val _totalAmountFormatted = MutableLiveData<String>()
    val totalAmountFormatted: LiveData<String> = _totalAmountFormatted

    @RequiresApi(Build.VERSION_CODES.N)
    fun getUserCart() {
        viewModelScope.launch {
            viewModelScope.launch {
                _status.value = OnlineShoppingApiStatus.LOADING
                try {
                    _cart.value =
                        OnlineShoppingApi.retrofitService.getCarts(userId.toString()).last()
                    _status.value = OnlineShoppingApiStatus.DONE
                    getCartProductsDetailsList()
                } catch (e: Exception) {
                    _status.value = OnlineShoppingApiStatus.ERROR
                    Log.d("CartViewModel", e.toString())
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCartProductsList() {
        viewModelScope.launch {
            viewModelScope.launch {
                _status.value = OnlineShoppingApiStatus.LOADING
                try {
                    _cartProducts.value =
                        OnlineShoppingApi.retrofitService.getCarts(userId.toString())
                            .last().products
                    _status.value = OnlineShoppingApiStatus.DONE
                    getCartProductsDetailsList()
                } catch (e: Exception) {
                    _status.value = OnlineShoppingApiStatus.ERROR
                    Log.d("CartViewModel", e.toString())
                    _cartProducts.value = listOf()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getCartProductsDetailsList() {
        viewModelScope.launch {
            viewModelScope.launch {
                _status.value = OnlineShoppingApiStatus.LOADING
                try {
                    _cartProductsDetails.value = OnlineShoppingApi.retrofitService.getProducts()
                        .filter {
                            cartProducts.value?.map { cartProduct -> cartProduct.productId }
                                ?.contains(it.id) ?: false
                        }
                    _status.value = OnlineShoppingApiStatus.DONE

                    calculateTotalAmount()
                    _totalAmountFormatted.value =
                        NumberFormat.getInstance(CURRENCYSTYLE).format(totalAmount.value)

                } catch (e: Exception) {
                    _status.value = OnlineShoppingApiStatus.ERROR
                    Log.d("CartViewModel", e.toString())
                    _cartProductsDetails.value = listOf()
                }
            }
        }
    }

    fun updateCartDetails() {
        viewModelScope.launch {
            viewModelScope.launch {
                _status.value = OnlineShoppingApiStatus.LOADING
                try {
                    _cart.value =
                        OnlineShoppingApi.retrofitService.updateCart(
                            cart.value?.id.toString(),
                            cart.value!!
                        )
                    _status.value = OnlineShoppingApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = OnlineShoppingApiStatus.ERROR
                    Log.d("CartViewModel", e.toString())
                }
            }
        }
    }

    fun removeCartItem() {
        viewModelScope.launch {
            viewModelScope.launch {
                _status.value = OnlineShoppingApiStatus.LOADING
                try {
                    _cart.value =
                        OnlineShoppingApi.retrofitService.removeItemCart(
                            cart.value?.id.toString(),
                            cart.value!!
                        )
                    _status.value = OnlineShoppingApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = OnlineShoppingApiStatus.ERROR
                    Log.d("CartViewModel", e.toString())
                }
            }
        }
    }

    fun emptyCart() {
        viewModelScope.launch {
            viewModelScope.launch {
                _status.value = OnlineShoppingApiStatus.LOADING
                try {
                    _cart.value =
                        OnlineShoppingApi.retrofitService.deleteCart(cart.value?.id.toString())
                    _status.value = OnlineShoppingApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = OnlineShoppingApiStatus.ERROR
                    Log.d("CartViewModel", e.toString())
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkout() {
        _cart.value?.date = LocalDate.now().toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun calculateTotalAmount() {
        _orderedPrices.value = cartProductsDetails.value?.sortedBy { it.id }?.map { it.price }
        _orderedQuantities.value =
            cartProducts.value?.sortedBy { it.productId }?.map { it.quantity }

        _totalAmount.value = orderedPrices.value?.zip(orderedQuantities.value!!) { op, oq ->
            op * oq
        }?.reduce { acc, d ->
            acc + d
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getProductPriceQuantityById(id: Int): String {
        return NumberFormat.getInstance(CURRENCYSTYLE).format((cartProductsDetails.value?.find {
            it.id == id
        }?.price ?: 0.0) * getQuantityById(id))
    }

    fun getProductPriceQuantityByIdAsDouble(id: Int): Double {
        return (cartProductsDetails.value?.find {
            it.id == id
        }?.price ?: 0.0) * getQuantityById(id)
    }

    fun getQuantityById(id: Int): Int {
        return cartProducts.value?.find {
            it.productId == id
        }?.quantity ?: 0
    }

    fun onProductClicked(product: Product) {
        _cartProductDetails.value = product
    }
}