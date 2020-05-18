package fr.ascotte.cv.kotlin.ui.experiences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import fr.ascotte.cv.kotlin.R
import fr.ascotte.cv.kotlin.data.`object`.*
import fr.ascotte.cv.kotlin.extensions.fromJson
import fr.ascotte.cv.kotlin.ui.ExpandableListAdapter
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_experiences.*

class ExperiencesFragment : Fragment() {

    val args : ExperiencesFragmentArgs by navArgs()

    var clients:List<Client> = listOf()
    var companies:List<Company> = listOf()
    var experiences:List<Experience> = listOf()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_experiences, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.getData()

        val hasMap = HashMap<Company, List<Client>>()
        hasMap.put(companies[0], clients)
        hasMap.put(companies[1], clients)
        hasMap.put(companies[2], clients)

        val adapter = ExpandableListAdapter(view.context, companies, hasMap)
        ui_expandable_list.setAdapter(adapter)

        // Expand group at the beginning
        for (i in 0 until adapter.groupCount)
            ui_expandable_list.expandGroup(i)
    }

    private fun getData() {

        companies = Gson().fromJson(args.companyList)
        clients = Gson().fromJson(args.clientList)
        experiences = Gson().fromJson(args.experienceList)
    }
}
