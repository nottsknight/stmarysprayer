package uk.nottsknight.stmarysprayer.prayer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uk.nottsknight.stmarysprayer.R

class EnglishPrayerBookFragment : Fragment() {

    companion object {
        fun newInstance() = EnglishPrayerBookFragment()
    }

    private lateinit var viewModel: EnglishPrayerBookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_english_prayer_book, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EnglishPrayerBookViewModel::class.java)
        // TODO: Use the ViewModel
    }

}