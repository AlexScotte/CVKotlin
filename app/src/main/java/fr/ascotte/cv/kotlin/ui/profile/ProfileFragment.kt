package fr.ascotte.cv.kotlin.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.gson.Gson
import fr.ascotte.cv.kotlin.R
import fr.ascotte.cv.kotlin.data.DataManager
import fr.ascotte.cv.kotlin.data.`object`.Profile
import fr.ascotte.cv.kotlin.data.local.LocalDataManager
import fr.ascotte.cv.kotlin.extensions.fromJson
import kotlinx.android.synthetic.main.activity_experience_details.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.ui_lbl_job


class ProfileFragment : Fragment(), DataManager.Protocol {

    val args: ProfileFragmentArgs by navArgs()
    var profile: Profile? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.getData()
        if(profile != null){

            activity?.title = "${profile?.firstName} ${profile?.lastName}"
            ui_lbl_job.text = profile?.job?.toUpperCase()

            val descr = profile?.description?.replace("\\n", "\n")
            ui_lbl_descr.text = descr

            Glide.with(this).load(profile?.imageUrl).override(400, 400).into(ui_img_profile)
            Glide.with(this).load(profile?.backgroundImageUrl)/*.override(300, 300)*/.into(ui_img_bckgrnd)
        }
    }

    private fun getData() {

        if(!args.profile.isNullOrBlank() && !args.profile.isNullOrEmpty())
            profile = Gson().fromJson(args.profile!!)
    }
}
