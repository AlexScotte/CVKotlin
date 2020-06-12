package fr.ascotte.cv.kotlin.ui.contact

import android.Manifest.permission.CALL_PHONE
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import fr.ascotte.cv.kotlin.R
import fr.ascotte.cv.kotlin.data.`object`.Contact
import fr.ascotte.cv.kotlin.data.`object`.ExternalLink
import fr.ascotte.cv.kotlin.extensions.fromJson
import kotlinx.android.synthetic.main.fragment_contact.*


class ContactFragment : Fragment(), LinkListAdapter.Delegate  {

    val args: ContactFragmentArgs by navArgs()
    lateinit var contact: Contact

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = getString(R.string.title_view_contact)
        this.manageToolbar()
        getData()
        ui_extlink_list.layoutManager = LinearLayoutManager(this.context)
        ui_extlink_list.adapter = LinkListAdapter(this.context!!, this, contact.externalLinks)

        if(contact.cvUrl.isEmpty() || contact.cvUrl.isBlank())
            ui_btn_download.isEnabled = false

        if(contact.phone.isEmpty() || contact.phone.isBlank()){

            ui_lbl_phone.visibility = View.GONE
            ui_img_phone.visibility = View.GONE
        }
        else{

            ui_lbl_phone.text = contact.phone
            ui_lbl_phone.setOnClickListener{
                onPhoneNumberClicked()
            }
            ui_img_phone.setOnClickListener{
                onPhoneNumberClicked()
            }
        }

        if(contact.email.isEmpty() || contact.email.isBlank()){

            ui_lbl_email.visibility = View.GONE
            ui_img_email.visibility = View.GONE
        }
        else{

            ui_lbl_email.text = contact.email
            ui_lbl_email.setOnClickListener{
                onEmailClicked()
            }
            ui_img_email.setOnClickListener{
                onEmailClicked()
            }
        }

        ui_btn_download.setOnClickListener{
            onDownloadButtonClicked()
        }
    }

    private fun manageToolbar(){

        val toolbar = activity?.findViewById(R.id.toolbar) as Toolbar
        val searchItem = toolbar.menu.findItem(R.id.action_search)
        searchItem?.isVisible = false
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

    private fun onPhoneNumberClicked(){

        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact.phone))
        if (ContextCompat.checkSelfPermission(activity?.applicationContext!!, CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
        } else {
            requestPermissions(Array<String>(1){CALL_PHONE}, 1)
        }
    }

    private fun onEmailClicked(){

        val intent = Intent(Intent.ACTION_SENDTO)
        val data = Uri.parse("mailto:${contact.email}")
        intent.data = data
        startActivity(intent)
    }
}
