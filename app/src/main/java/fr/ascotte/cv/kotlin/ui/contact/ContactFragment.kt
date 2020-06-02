package fr.ascotte.cv.kotlin.ui.contact

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.ascotte.cv.kotlin.R
import fr.ascotte.cv.kotlin.data.`object`.Contact
import fr.ascotte.cv.kotlin.data.`object`.ExternalLink
import fr.ascotte.cv.kotlin.data.`object`.Formation
import fr.ascotte.cv.kotlin.data.`object`.Profile
import fr.ascotte.cv.kotlin.extensions.fromJson
import fr.ascotte.cv.kotlin.ui.formation.FormationListAdapter
import fr.ascotte.cv.kotlin.ui.profile.ProfileFragmentArgs
import kotlinx.android.synthetic.main.fragment_contact.*
import kotlinx.android.synthetic.main.fragment_formation.*


class ContactFragment : Fragment(), LinkListAdapter.Delegate  {

    val args: ContactFragmentArgs by navArgs()
    lateinit var contact: Contact

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = getString(R.string.title_view_contact)
        getData()
        ui_link_list.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        ui_link_list.adapter = LinkListAdapter(this.context!!, this, contact.externalLinks)

        if(contact.cvUrl.isEmpty() || contact.cvUrl.isBlank())
            ui_btn_download.isEnabled = false

        ui_btn_download.setOnClickListener{
            onDownloadButtonClicked()
        }
    }

    private fun getData() {

        contact = Gson().fromJson(args.contact)
    }

    private fun onDownloadButtonClicked(){

        this.openLink(contact.cvUrl)
    }

    override fun linkClicked(link: ExternalLink, index: Int) {

        this.openLink(link.url)
    }

    private fun openLink(url:String){

        if(url.isNotEmpty() && url.isNotBlank()){

            val openUrl = Intent(Intent.ACTION_VIEW)
            openUrl.data = Uri.parse(url)
            startActivity(openUrl)
        }
    }
}
