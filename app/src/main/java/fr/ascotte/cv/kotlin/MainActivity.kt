package fr.ascotte.cv.kotlin

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.gson.Gson
import fr.ascotte.cv.kotlin.data.DataManager
import fr.ascotte.cv.kotlin.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, DataManager.Protocol {

    private lateinit var dataManager: DataManager
    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_profile, R.id.navigation_experiences, R.id.navigation_formation, R.id.navigation_contact))
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottom_Navigation_View.setupWithNavController(navController)
        bottom_Navigation_View.setOnNavigationItemSelectedListener(this)
        this.dataManager = DataManager(this, this)
        this.dataManager.createDatabase()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.navigation_profile -> {

                val bundle = this.prepareProfileArgs()
                navController.navigate(R.id.navigation_profile, bundle)
            }
            R.id.navigation_experiences -> {

                val bundle = this.prepareExperiencesArgs()
                navController.navigate(R.id.navigation_experiences, bundle)
            }
            R.id.navigation_formation -> {

                val bundle = this.prepareFormationsArgs()
                navController.navigate(R.id.navigation_formation, bundle)
            }
            R.id.navigation_contact -> {

                val bundle = this.prepareContactArgs()
                navController.navigate(R.id.navigation_contact, bundle)
            }
            else -> {
                NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item)
            }
        }
        return true
    }
    override fun localProfileCreated() {

        val bundle = this.prepareProfileArgs()
        navController.navigate(R.id.navigation_profile, bundle)
    }

    private fun prepareProfileArgs() : Bundle {

        val bundle = Bundle()
        val profile = dataManager.localDataManager.getProfile()
        val skills = dataManager.localDataManager.getCompetences()

        var json = Gson().toJson(profile)
        bundle.putString("profile", json)

        json = Gson().toJson(skills)
        bundle.putString("skillList", json)

        return bundle
    }

    private fun prepareExperiencesArgs() : Bundle {

        val bundle = Bundle()
        val companies = dataManager.localDataManager.getCompanies().sortedByDescending { it.id }

        var json = Gson().toJson(companies)
        bundle.putString("companyList", json)

        return bundle
    }

    private fun prepareFormationsArgs() : Bundle {

        val bundle = Bundle()
        val formations = dataManager.localDataManager.getFormations().sortedByDescending { it.id }

        var json = Gson().toJson(formations)
        bundle.putString("formationList", json)

        return bundle
    }

    private fun prepareContactArgs() : Bundle {

        val bundle = Bundle()
        val contact = dataManager.localDataManager.getContact()

        var json = Gson().toJson(contact)
        bundle.putString("contact", json)

        return bundle
    }
}

