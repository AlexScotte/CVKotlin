package fr.ascotte.cv.kotlin.ui.experiences

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import fr.ascotte.cv.kotlin.R
import fr.ascotte.cv.kotlin.data.`object`.Client
import fr.ascotte.cv.kotlin.data.`object`.Company
import kotlinx.android.synthetic.main.activity_experience_details.*

class ExperienceDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experience_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val company = intent.getSerializableExtra("company") as Company
        val client = intent.getSerializableExtra("client") as Client

        title = client.name.toUpperCase()
        ui_lbl_duration.text = "(${client.experience?.duration})"
        ui_lbl_job.text = client.experience?.job?.toUpperCase()

        Glide.with(this).load(client.imageUrl).override(300, 300).into(ui_img_client)
        ui_img_client.setOnClickListener{

            if(!client.site.isNullOrEmpty()){

                val openUrl = Intent(android.content.Intent.ACTION_VIEW)
                openUrl.data = Uri.parse(client.site)
                startActivity(openUrl)
            }
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
