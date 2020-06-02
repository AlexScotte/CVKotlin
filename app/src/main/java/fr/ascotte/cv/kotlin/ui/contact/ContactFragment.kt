package fr.ascotte.cv.kotlin.ui.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import fr.ascotte.cv.kotlin.R
import fr.ascotte.cv.kotlin.data.`object`.Contact
import fr.ascotte.cv.kotlin.data.`object`.Profile
import fr.ascotte.cv.kotlin.extensions.fromJson
import fr.ascotte.cv.kotlin.ui.profile.ProfileFragmentArgs


class ContactFragment : Fragment() {

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

    }

    private fun getData() {

        contact = Gson().fromJson(args.contact)
    }
}
