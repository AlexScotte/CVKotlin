package fr.ascotte.cv.kotlin.ui.experiences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import fr.ascotte.cv.kotlin.R
import fr.ascotte.cv.kotlin.ui.ExpandableListAdapter
import kotlinx.android.synthetic.main.fragment_experiences.*

class ExperiencesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_experiences, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var listHeader = listOf("111", "22222", "333333")
        var child1 = listOf("1", "11", "111", "11111")
        var child2 = listOf("2", "22", "222", "22222")
        var child3 = listOf("3", "33", "333", "33333")

        val dict = HashMap<String, List<String>>()
        dict.put(listHeader[0], child1)
        dict.put(listHeader[1], child2)
        dict.put(listHeader[2], child3)

        val adapter = ExpandableListAdapter(view.context, listHeader, dict)
        ui_expandable_list.setAdapter(adapter)
    }

}
