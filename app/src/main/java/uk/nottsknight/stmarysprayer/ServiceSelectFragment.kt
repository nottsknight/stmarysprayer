package uk.nottsknight.stmarysprayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.ListFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import uk.nottsknight.stmarysprayer.databinding.FragmentServiceSelectBinding

class ServiceSelectFragment : ListFragment() {
    private val viewModel: ServiceSelectViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // data binding
        val binding = DataBindingUtil.inflate<FragmentServiceSelectBinding>(
            inflater,
            R.layout.fragment_service_select,
            container,
            false
        )
        viewModel.englishPrayerBookCallback = NavigationCallback {
            findNavController().navigate(R.id.action_serviceSelectFragment_to_englishPrayerBookFragment)
        }
        viewModel.commonWorshipCallback = NavigationCallback {
            findNavController().navigate(R.id.action_serviceSelectFragment_to_commonWorshipFragment)
        }
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                context?.resources?.getStringArray(R.array.sselect_services) ?: arrayOf()
            )
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) = when (position) {
        0 -> findNavController().navigate(R.id.action_serviceSelectFragment_to_englishPrayerBookFragment)
        1 -> findNavController().navigate(R.id.action_serviceSelectFragment_to_commonWorshipFragment)
        else -> Unit
    }
}

fun interface NavigationCallback {
    fun performNavigation()
}