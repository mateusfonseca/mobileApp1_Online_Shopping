package ie.dorset.student_24088.project.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ie.dorset.student_24088.project.R
import ie.dorset.student_24088.project.adapter.ItemListAdapter
import ie.dorset.student_24088.project.adapter.ItemListener
import ie.dorset.student_24088.project.databinding.FragmentCartBinding
import ie.dorset.student_24088.project.model.Product
import ie.dorset.student_24088.project.viewmodel.CartViewModel

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserCart()
        viewModel.getCartProductsList()

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recyclerView.adapter = ItemListAdapter("CartFragment", ItemListener { data ->
            viewModel.onProductClicked(data as Product)

            findNavController().navigate(R.id.action_viewPagerFragment_to_cartProductFragment)
        })

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel Name"
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("My_Notification_Channel", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                activity?.getSystemService(
                    Context.NOTIFICATION_SERVICE
                ) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        binding.checkout.setOnClickListener {
            viewModel.checkout()
            val args = bundleOf("order" to viewModel.cart.value)

            val pendingIntent = NavDeepLinkBuilder(requireContext())
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.viewPagerFragment)
                .addDestination(R.id.orderFragment)
                .setArguments(args)
                .createPendingIntent()

            val builder = NotificationCompat.Builder(requireActivity(), "My_Notification_Channel")
                .setSmallIcon(R.drawable.ic_checkout)
                .setContentTitle("Order Placed!")
                .setContentText("A new order has just been placed into your account.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(requireActivity())) {
                // notificationId is a unique int for each notification that you must define
                notify(0, builder.build())
            }

            Snackbar.make(
                view, "Your cart has been checked-out!",
                BaseTransientBottomBar.LENGTH_LONG
            ).show()

            findNavController().navigateUp()
        }

        binding.empty.setOnClickListener {
            viewModel.emptyCart()

            Snackbar.make(
                view, "Your cart has been emptied!",
                BaseTransientBottomBar.LENGTH_LONG
            ).show()

            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}