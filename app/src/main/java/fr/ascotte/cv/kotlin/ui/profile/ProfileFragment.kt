package fr.ascotte.cv.kotlin.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import fr.ascotte.cv.kotlin.R
import fr.ascotte.cv.kotlin.data.DataManager
import fr.ascotte.cv.kotlin.data.`object`.Company
import fr.ascotte.cv.kotlin.data.`object`.Competence
import fr.ascotte.cv.kotlin.data.`object`.Profile
import fr.ascotte.cv.kotlin.extensions.fromJson
import kotlinx.android.synthetic.main.activity_experience_details.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.ui_chpGrp_skills
import kotlinx.android.synthetic.main.fragment_profile.ui_lbl_job


class ProfileFragment : Fragment(), DataManager.Protocol {

    val args: ProfileFragmentArgs by navArgs()
    var profile: Profile? = null
    var skills:List<Competence> = listOf()

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
            ui_lbl_location.text = profile?.location

            val descr = profile?.description?.replace("\\n", "\n")
            ui_lbl_profile_descr.text = descr

            Glide.with(this).load(profile?.imageUrl).override(400, 400).into(ui_img_profile)
            Glide.with(this).load(profile?.backgroundImageUrl)/*.override(300, 300)*/.into(ui_img_bckgrnd)

            if(profile != null){

                for (hobby in profile?.hobbies!!){

                    val chip = Chip(ui_chpGrp_hobbies.context)
                    chip.text = hobby.name
                    chip.isClickable = false
                    chip.isCheckable = false
                    ui_chpGrp_hobbies.addView(chip)
                }
            }

            for (skill in skills){

                val chip = Chip(ui_chpGrp_skills.context)
                chip.text = skill.name
                chip.isClickable = false
                chip.isCheckable = false
                ui_chpGrp_skills.addView(chip)
            }
        }
    }

    private fun getData() {

        if(!args.profile.isNullOrBlank() && !args.profile.isNullOrEmpty())
            profile = Gson().fromJson(args.profile!!)

        if(!args.skillList.isNullOrBlank() && !args.skillList.isNullOrEmpty())
            skills = Gson().fromJson(args.skillList!!)
    }
}
