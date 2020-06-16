package fr.ascotte.cv.kotlin.ui.experiences

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import fr.ascotte.cv.kotlin.R
import fr.ascotte.cv.kotlin.data.`object`.Client
import fr.ascotte.cv.kotlin.data.`object`.Company
import kotlinx.android.synthetic.main.activity_experience_details.*

class ExperienceDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experience_details)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val company = intent.getSerializableExtra("company") as Company
        val client = intent.getSerializableExtra("client") as Client

        title = client.name.toUpperCase()

        if(client.experience?.duration.isNullOrEmpty())
            ui_lbl_duration.visibility = View.GONE
        else
            ui_lbl_duration.text = "(${client.experience?.duration})"

        ui_lbl_job.text = client.experience?.job?.toUpperCase()

        if(client.experience?.details?.context.isNullOrEmpty()){

            ui_lbl_context.visibility = View.GONE
            ui_lbl_context_descr.visibility = View.GONE
        }
        else{

            val contxt = client.experience?.details?.context?.replace("\\n", "\n")
            ui_lbl_context_descr.text = contxt
        }


        val missions = client.experience?.details?.missions?.replace("\\n", "\n")
        ui_lbl_missions_descr.text = missions

        Glide.with(this).load(client.imageUrl).override(300, 300).into(ui_img_client)
        ui_img_client.setOnClickListener{

            if(client.site.isNotEmpty()){

                val openUrl = Intent(android.content.Intent.ACTION_VIEW)
                openUrl.data = Uri.parse(client.site)
                startActivity(openUrl)
            }
        }

        for (comp in client.experience!!.skills){

            val chip = Chip(ui_chpGrp_skills.context)
            chip.text = comp.name
            chip.isClickable = false
            chip.isCheckable = false
            ui_chpGrp_skills.addView(chip)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
