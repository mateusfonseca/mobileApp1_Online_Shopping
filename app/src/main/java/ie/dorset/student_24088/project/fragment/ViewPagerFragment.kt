package ie.dorset.student_24088.project.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import ie.dorset.student_24088.project.MainActivity
import ie.dorset.student_24088.project.R
import ie.dorset.student_24088.project.databinding.FragmentViewPagerBinding

class ViewPagerFragment : Fragment() {
    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val view = binding.root

        (activity as MainActivity).supportActionBar?.apply {
            title = resources.getString(R.string.app_name)
            subtitle = "Home"
        }

        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 4
            override fun createFragment(position: Int): Fragment =
                when (position) {
                    0 -> CategoryListFragment()
                    1 -> CartFragment()
                    2 -> OrderListFragment()
                    else -> UserFragment()
                }
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Shop"
                    tab.icon = getDrawable(resources, R.drawable.ic_store, null)
                }
                1 -> {
                    tab.text = "Cart"
                    tab.icon = getDrawable(resources, R.drawable.ic_cart, null)
                }
                2 -> {
                    tab.text = "Orders"
                    tab.icon = getDrawable(resources, R.drawable.ic_orders, null)
                }
                3 -> {
                    tab.text = "User"
                    tab.icon = getDrawable(resources, R.drawable.ic_user_account, null)
                }
            }
        }.attach()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.viewPager.currentItem == 0)
                        activity?.finish()
                    else
                        binding.viewPager.currentItem--
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}