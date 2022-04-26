package ie.dorset.student_24088.project.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ie.dorset.student_24088.project.MainActivity
import ie.dorset.student_24088.project.R
import ie.dorset.student_24088.project.databinding.FragmentSignUpBinding
import ie.dorset.student_24088.project.model.Address
import ie.dorset.student_24088.project.model.Geolocation
import ie.dorset.student_24088.project.model.Name
import ie.dorset.student_24088.project.model.User
import ie.dorset.student_24088.project.network.OnlineShoppingApi
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root

        (activity as MainActivity).supportActionBar?.apply {
            title = resources.getString(R.string.app_name)
            subtitle = "Sign Up"
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Uncomment following block to set up test driver
//        binding.run {
//            firstName.setText("John", TextView.BufferType.EDITABLE)
//            lastName.setText("Doe", TextView.BufferType.EDITABLE)
//            phone.setText("1-570-236-7033", TextView.BufferType.EDITABLE)
//            city.setText("kilcoole", TextView.BufferType.EDITABLE)
//            street.setText("new road", TextView.BufferType.EDITABLE)
//            number.setText("7835", TextView.BufferType.EDITABLE)
//            zipCode.setText("12926-3874", TextView.BufferType.EDITABLE)
//            latitude.setText("-37.3159", TextView.BufferType.EDITABLE)
//            longitude.setText("81.1496", TextView.BufferType.EDITABLE)
//            email.setText("John@gmail.com", TextView.BufferType.EDITABLE)
//            username.setText("johnd", TextView.BufferType.EDITABLE)
//            password.setText("m38rmF$", TextView.BufferType.EDITABLE)
//        }

        binding.apply {
            buttonConfirm.setOnClickListener {
                addUserDetails(view)
            }

            buttonCancel.setOnClickListener {
                findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addUserDetails(view: View) {
        var user: User
        try {
            binding.apply {
                user = User(
                    -1,
                    email.text.toString(),
                    username.text.toString(),
                    password.text.toString(),
                    Name(
                        firstName.text.toString(),
                        lastName.text.toString()
                    ),
                    Address(
                        city.text.toString(),
                        street.text.toString(),
                        number.text.toString().toInt(),
                        zipCode.text.toString(),
                        Geolocation(),
                    ),
                    phone.text.toString()
                )
            }

            lifecycleScope.launch {
                try {
                    Log.d("SingUp try", user.toString())
                    OnlineShoppingApi.retrofitService.signUp(user).also {
                        Log.d("SingUp try", it.toString())
                    }
                } catch (e: Exception) {
                    Log.d("SingUp catch", e.toString())
                }
            }
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        } catch (e: Exception) {
            Log.d("SignUpFragment", e.toString())
            Snackbar.make(
                view, "All fields are required! Numbers must be numbers!",
                BaseTransientBottomBar.LENGTH_LONG
            ).show()
        }
    }
}