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
import fr.ascotte.cv.kotlin.data.`object`.Client
import fr.ascotte.cv.kotlin.data.`object`.RealmClient
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

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
        this.dataManager = DataManager(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.navigation_experiences -> {

                val bundle = this.prepareExperiencesArgs()
                navController.navigate(R.id.navigation_experiences, bundle)
            }
            else -> {
                NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item)
            }
        }
        return true
    }

    private fun prepareExperiencesArgs() : Bundle {

        val bundle = Bundle()
        val companies = dataManager.localDataManager.getCompanies().sortedByDescending { it.id }

        var json = Gson().toJson(companies)
        bundle.putString("companyList", json)

        return bundle
    }
}

