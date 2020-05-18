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
import fr.ascotte.cv.kotlin.data.`object`.RealmClient
import fr.ascotte.cv.kotlin.data.`object`.RealmCompany
import fr.ascotte.cv.kotlin.data.`object`.RealmExperience
import fr.ascotte.cv.kotlin.extensions.fromJson
import fr.ascotte.cv.kotlin.ui.ExpandableListAdapter
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_experiences.*

class ExperiencesFragment : Fragment() {

    val args : ExperiencesFragmentArgs by navArgs()

    var clients:List<RealmClient> = listOf()
    var companies:List<RealmCompany> = listOf()
    var experiences:List<RealmExperience> = listOf()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_experiences, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.getData()

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

        // Expand group at the beginning
        for (i in 0 until adapter.groupCount)
            ui_expandable_list.expandGroup(i)
    }

    private fun getData() {

        clients = Gson().fromJson(args.clientList)
        companies = Gson().fromJson(args.companyList)
        experiences = Gson().fromJson(args.experienceList)
    }
}
