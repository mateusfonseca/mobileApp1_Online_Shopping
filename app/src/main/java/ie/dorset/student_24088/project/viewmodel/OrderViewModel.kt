package ie.dorset.student_24088.project.viewmodel

import android.icu.text.NumberFormat
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

class OrderViewModel : ViewModel() {

    private val _status = MutableLiveData<OnlineShoppingApiStatus>()
    val status: LiveData<OnlineShoppingApiStatus> = _status

    private val _orders = MutableLiveData<List<Cart>>()
    val orders: LiveData<List<Cart>> = _orders

    private val _order = MutableLiveData<Cart>()
    val order: LiveData<Cart> = _order

    private val _orderProducts = MutableLiveData<List<CartProduct>>()
    private val orderProducts: LiveData<List<CartProduct>> = _orderProducts

    private val _orderProductsDetails = MutableLiveData<List<Product>>()
    val orderProductsDetails: LiveData<List<Product>> = _orderProductsDetails

    private val _orderProductDetails = MutableLiveData<Product>()
    val orderProductDetails: LiveData<Product> = _orderProductDetails

    private val _orderedQuantities = MutableLiveData<List<Int>>()
    private val orderedQuantities: LiveData<List<Int>> = _orderedQuantities

    private val _orderedPrices = MutableLiveData<List<Double>>()
    private val orderedPrices: LiveData<List<Double>> = _orderedPrices

    private val _totalAmount = MutableLiveData<Double>()
    private val totalAmount: LiveData<Double> = _totalAmount

    private val _totalAmountFormatted = MutableLiveData<String>()
    val totalAmountFormatted: LiveData<String> = _totalAmountFormatted

    fun getOrdersList() {
        viewModelScope.launch {
            viewModelScope.launch {
                _status.value = OnlineShoppingApiStatus.LOADING
                try {
                    _orders.value =
                        OnlineShoppingApi.retrofitService.getCarts(userId.toString()).dropLast(1)
                    _status.value = OnlineShoppingApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = OnlineShoppingApiStatus.ERROR
                    Log.d("OrderViewModel", e.toString())
                    _orders.value = listOf()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getOrderProductsList(order: Cart) {
        viewModelScope.launch {
            viewModelScope.launch {
                _status.value = OnlineShoppingApiStatus.LOADING
                try {
                    _orderProducts.value = order.products
                    _status.value = OnlineShoppingApiStatus.DONE
                    getOrderProductsDetailsList()
                } catch (e: Exception) {
                    _status.value = OnlineShoppingApiStatus.ERROR
                    Log.d("OrderViewModel", e.toString())
                    _orderProducts.value = listOf()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getOrderProductsDetailsList() {
        viewModelScope.launch {
            viewModelScope.launch {
                _status.value = OnlineShoppingApiStatus.LOADING
                try {
                    _orderProductsDetails.value = OnlineShoppingApi.retrofitService.getProducts()
                        .filter {
                            orderProducts.value?.map { orderProduct -> orderProduct.productId }
                                ?.contains(it.id) ?: false
                        }
                    _status.value = OnlineShoppingApiStatus.DONE

                    calculateTotalAmount()
                    _totalAmountFormatted.value =
                        NumberFormat.getInstance(NumberFormat.CURRENCYSTYLE)
                            .format(totalAmount.value)

                } catch (e: Exception) {
                    _status.value = OnlineShoppingApiStatus.ERROR
                    Log.d("OrderViewModel", e.toString())
                    _orderProductsDetails.value = listOf()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun calculateTotalAmount() {
        _orderedPrices.value = orderProductsDetails.value?.sortedBy { it.id }?.map { it.price }
        _orderedQuantities.value =
            orderProducts.value?.sortedBy { it.productId }?.map { it.quantity }

        _totalAmount.value = orderedPrices.value?.zip(orderedQuantities.value!!) { op, oq ->
            op * oq
        }?.reduce { acc, d ->
            acc + d
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getProductPriceQuantityById(id: Int): String {
        return NumberFormat.getInstance(NumberFormat.CURRENCYSTYLE)
            .format((orderProductsDetails.value?.find {
                it.id == id
            }?.price ?: 0.0) * getQuantityById(id))
    }

    fun getQuantityById(id: Int): Int {
        return orderProducts.value?.find {
            it.productId == id
        }?.quantity ?: 0
    }

    fun onOrderClicked(order: Cart): Cart {
        _order.value = order
        return order
    }

    fun onProductClicked(product: Product) {
        _orderProductDetails.value = product
    }

    fun onCheckOut(order: Cart) {
        _order.value = order
    }
}