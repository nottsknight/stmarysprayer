package uk.nottsknight.stmarysprayer.prayer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uk.nottsknight.stmarysprayer.R

class CommonWorshipFragment : Fragment() {

    companion object {
        fun newInstance() = CommonWorshipFragment()
    }

    private lateinit var viewModel: CommonWorshipViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_common_worship, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CommonWorshipViewModel::class.java)
        // TODO: Use the ViewModel
    }

}