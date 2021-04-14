package uk.nottsknight.stmarysprayer

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment
import androidx.navigation.fragment.findNavController

class ServiceSelectFragment : ListFragment() {
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
