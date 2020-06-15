package fr.ascotte.cv.kotlin.ui.experiences

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import fr.ascotte.cv.kotlin.R
import fr.ascotte.cv.kotlin.data.`object`.Client
import fr.ascotte.cv.kotlin.data.`object`.Company
import fr.ascotte.cv.kotlin.extensions.fromJson
import kotlinx.android.synthetic.main.fragment_experiences.*


class ExperiencesFragment : Fragment(), ExpandableListAdapter.Delegate {

    private lateinit var adapter: ExpandableListAdapter
    val args : ExperiencesFragmentArgs by navArgs()
    var companies:MutableList<Company> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_experiences, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = getString(R.string.title_view_experiences)
        this.getData()

        var comp = mutableListOf<Company>()
        comp.addAll(companies)
        val hashMap = createHashMap()
        adapter = ExpandableListAdapter(
            this,
            view.context,
            comp,
            hashMap
        )
        ui_expandable_list.setAdapter(adapter)

        // Expand group at the beginning
        for (i in 0 until adapter.groupCount)
            ui_expandable_list.expandGroup(i)
    }



    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater)

        menuInflater.inflate(R.menu.toolbar_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView
        searchItem.isVisible = true

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{

            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                searchView.requestFocus()
                searchView.queryHint = getString(R.string.toolbar_search)
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {

                loadMatchingData(null)
                return true
            }
        })


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextChange(newText: String?): Boolean {

                loadMatchingData(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }
        })
    }

    private fun loadMatchingData(skillName: String?) {

        if(skillName != null && skillName.length > 1){

            val tmpCompanies = mutableListOf<Company>()

            val hasMap = HashMap<Company, List<Client>>()

            for (company in companies){

                val tmpClients = mutableListOf<Client>()
                for(client in company.clients){

                    val matching = client.experience?.skills?.any{ s -> s.name.contains(skillName, ignoreCase = true) }
                    if(matching != null && matching){

                        if(!tmpCompanies.contains(company))
                            tmpCompanies.add(company)

                        tmpClients.add(client)
                    }
                }

                hasMap[company] = tmpClients
            }

            adapter.updateData(tmpCompanies, hasMap)
        }
        else{

            val hashMap = createHashMap()
            adapter.updateData(companies, hashMap)
        }
    }

    private fun createHashMap() : HashMap<Company, List<Client>>{

        val hasMap = HashMap<Company, List<Client>>()
        for (company in companies){

            hasMap[company] = company.clients
        }

        return hasMap
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
