package fr.ascotte.cv.kotlin.ui.experiences

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView.OnChildClickListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import fr.ascotte.cv.kotlin.R
import fr.ascotte.cv.kotlin.data.`object`.Client
import fr.ascotte.cv.kotlin.data.`object`.Company
import fr.ascotte.cv.kotlin.data.`object`.Competence
import fr.ascotte.cv.kotlin.data.`object`.Experience
import fr.ascotte.cv.kotlin.extensions.fromJson
import fr.ascotte.cv.kotlin.ui.ExpandableListAdapter
import kotlinx.android.synthetic.main.fragment_experiences.*


class ExperiencesFragment : Fragment(), ExpandableListAdapter.Delegate {

    val args : ExperiencesFragmentArgs by navArgs()
    var companies:List<Company> = listOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_experiences, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = getString(R.string.title_view_experiences)
        this.getData()

        val hasMap = HashMap<Company, List<Client>>()
        for (company in companies){

            hasMap.put(company, company.clients)
        }

        val adapter = ExpandableListAdapter(this, view.context, companies, hasMap)
        ui_expandable_list.setAdapter(adapter)

        // Expand group at the beginning
        for (i in 0 until adapter.groupCount)
            ui_expandable_list.expandGroup(i)
    }

    private fun getData() {

        companies = Gson().fromJson(args.companyList)
    }

    override fun experienceClicked(company: Company, client: Client) {

        val intent = Intent(activity, ExperienceDetailsActivity::class.java)
        intent.putExtra("company", company)
        intent.putExtra("client", client)
        startActivity(intent)
    }
}
