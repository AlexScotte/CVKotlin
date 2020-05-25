package fr.ascotte.cv.kotlin.ui.formation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import fr.ascotte.cv.kotlin.R


class FormationFragment : Fragment() {

    companion object {
        fun newInstance() = FormationFragment()
    }

    private lateinit var viewModel: FormationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_formation, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FormationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
