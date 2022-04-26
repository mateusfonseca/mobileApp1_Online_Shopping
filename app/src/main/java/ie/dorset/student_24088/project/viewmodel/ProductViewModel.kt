package ie.dorset.student_24088.project.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ie.dorset.student_24088.project.model.Product
import ie.dorset.student_24088.project.network.OnlineShoppingApi
import ie.dorset.student_24088.project.network.OnlineShoppingApiStatus
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val _status = MutableLiveData<OnlineShoppingApiStatus>()
    val status: LiveData<OnlineShoppingApiStatus> = _status

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    fun getProductsList(categoryName: String) {
        viewModelScope.launch {
            viewModelScope.launch {
                _status.value = OnlineShoppingApiStatus.LOADING
                try {
                    _products.value = OnlineShoppingApi.retrofitService.getProducts(categoryName)
                    _status.value = OnlineShoppingApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = OnlineShoppingApiStatus.ERROR
                    Log.d("ProductViewModel", e.toString())
                    _products.value = listOf()
                }
            }
        }
    }

    fun getProductsList(productId: Int) {
        viewModelScope.launch {
            viewModelScope.launch {
                _status.value = OnlineShoppingApiStatus.LOADING
                try {
                    _product.value = OnlineShoppingApi.retrofitService.getProducts().find {
                        it.id == productId
                    }
                    _status.value = OnlineShoppingApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = OnlineShoppingApiStatus.ERROR
                    Log.d("ProductViewModel", e.toString())
                }
            }
        }
    }

    fun onProductClicked(product: Product) {
        _product.value = product
    }
}