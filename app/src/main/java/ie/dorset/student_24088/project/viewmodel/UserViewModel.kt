package ie.dorset.student_24088.project.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ie.dorset.student_24088.project.model.User
import ie.dorset.student_24088.project.network.OnlineShoppingApi
import ie.dorset.student_24088.project.network.OnlineShoppingApiStatus
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val _status = MutableLiveData<OnlineShoppingApiStatus>()
    val status: LiveData<OnlineShoppingApiStatus> = _status

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    @RequiresApi(Build.VERSION_CODES.O)
    fun getUserDetails(userId: Int) {
        viewModelScope.launch {
            viewModelScope.launch {
                _status.value = OnlineShoppingApiStatus.LOADING
                try {
                    _user.value = OnlineShoppingApi.retrofitService.getUser(userId.toString())
                    _status.value = OnlineShoppingApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = OnlineShoppingApiStatus.ERROR
                    Log.d("UserViewModel", e.toString())
                }
            }
        }
    }
}