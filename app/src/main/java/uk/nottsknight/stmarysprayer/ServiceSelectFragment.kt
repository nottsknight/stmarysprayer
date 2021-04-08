package uk.nottsknight.stmarysprayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import uk.nottsknight.stmarysprayer.databinding.FragmentServiceSelectBinding

class ServiceSelectFragment : Fragment() {

    companion object {
        fun newInstance() = ServiceSelectFragment()
    }

    private val viewModel: ServiceSelectViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentServiceSelectBinding>(
            inflater,
            R.layout.fragment_service_select,
            container,
            false
        )
        binding.viewModel = viewModel
        return binding.root
    }

}