package ie.dorset.student_24088.project.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ie.dorset.student_24088.project.model.Category
import ie.dorset.student_24088.project.network.OnlineShoppingApi
import ie.dorset.student_24088.project.network.OnlineShoppingApiStatus
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    private val _status = MutableLiveData<OnlineShoppingApiStatus>()
    val status: LiveData<OnlineShoppingApiStatus> = _status

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _category = MutableLiveData<Category>()
    val category: LiveData<Category> = _category

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCategoriesList() {
        viewModelScope.launch {
            viewModelScope.launch {
                _status.value = OnlineShoppingApiStatus.LOADING
                try {
                    _categories.value =
                        convertToCategoryList(OnlineShoppingApi.retrofitService.getCategories())
                    _status.value = OnlineShoppingApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = OnlineShoppingApiStatus.ERROR
                    _categories.value = listOf()
                    Log.d("CategoryViewModel", e.toString())
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertToCategoryList(categories: List<String>): List<Category> {
        var i = 1
        val categoriesList = mutableListOf<Category>()

        categories.forEach {
            categoriesList.add(Category(i, it))
            i++
        }

        return categoriesList
    }

    fun onCategoryClicked(category: Category): String {
        _category.value = category

        return _category.value!!.name
    }
}