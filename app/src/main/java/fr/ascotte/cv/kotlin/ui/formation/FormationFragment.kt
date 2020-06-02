package fr.ascotte.cv.kotlin.ui.formation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import fr.ascotte.cv.kotlin.R
import fr.ascotte.cv.kotlin.data.`object`.Formation
import fr.ascotte.cv.kotlin.extensions.fromJson
import kotlinx.android.synthetic.main.fragment_formation.*


class FormationFragment : Fragment(), FormationListAdapter.Delegate {

    val args : FormationFragmentArgs by navArgs()
    var formations:List<Formation> = listOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_formation, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = getString(R.string.title_view_formation)

        this.getData()
        ui_formation_list.layoutManager = LinearLayoutManager(this.context)
        ui_formation_list.adapter = FormationListAdapter(this, formations)
    }


    private fun getData() {

        formations = Gson().fromJson(args.formationList)
    }

    override fun formationClicked(formation: Formation, index: Int) {

        if(formation.url.isNotEmpty() && formation.url.isNotBlank()){
            val openUrl = Intent(Intent.ACTION_VIEW)
            openUrl.data = Uri.parse(formation.url)
            startActivity(openUrl)
        }
    }
}
