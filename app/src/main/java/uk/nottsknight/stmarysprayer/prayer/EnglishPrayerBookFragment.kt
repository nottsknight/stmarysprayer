package uk.nottsknight.stmarysprayer.prayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import uk.nottsknight.stmarysprayer.R
import uk.nottsknight.stmarysprayer.databinding.FragmentEnglishPrayerBookBinding

class EnglishPrayerBookFragment : Fragment() {

    companion object {
        fun newInstance() = EnglishPrayerBookFragment()
    }

    private val viewModel: EnglishPrayerBookViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentEnglishPrayerBookBinding>(
            inflater,
            R.layout.fragment_english_prayer_book,
            container,
            false
        )
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initialiseTexts()
    }

}