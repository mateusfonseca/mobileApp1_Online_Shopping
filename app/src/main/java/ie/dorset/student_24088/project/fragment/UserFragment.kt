package ie.dorset.student_24088.project.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ie.dorset.student_24088.project.R
import ie.dorset.student_24088.project.databinding.FragmentUserBinding
import ie.dorset.student_24088.project.model.Session.userId
import ie.dorset.student_24088.project.viewmodel.UserViewModel

class UserFragment : Fragment() {
    private val viewModel: UserViewModel by activityViewModels()
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)

        viewModel.getUserDetails(userId)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref =
            activity?.getSharedPreferences("sessionData", Context.MODE_PRIVATE) ?: return

        binding.about.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_aboutFragment)
        }

        binding.logout.setOnClickListener {
            sharedPref.edit().clear().apply()

            Log.d("signup", sharedPref.getInt("userId", -1).toString())
            Log.d("signup", sharedPref.getString("token", null).toString())

            findNavController().navigate(R.id.action_viewPagerFragment_to_signIntFragment)
        }
    }
}