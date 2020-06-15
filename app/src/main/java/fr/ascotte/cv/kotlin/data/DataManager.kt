package fr.ascotte.cv.kotlin.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings.Global.getString
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import fr.ascotte.cv.kotlin.R
import fr.ascotte.cv.kotlin.data.local.LocalDataManager
import fr.ascotte.cv.kotlin.data.remote.RemoteDataProvider
import kotlin.system.exitProcess


class DataManager(val context: Context, private val protocol:Protocol?)  {

    interface Protocol{
        fun localProfileCreated(){}
    }

    val localDataManager:LocalDataManager = LocalDataManager()
    private val dataProvider:RemoteDataProvider = RemoteDataProvider(context)

    fun createDatabase(){

        var isNetworkConnected = isNetworkAvailable(context)

        val localDatabaseVersion = localDataManager.getLocalDatabaseVersion()
        if(localDatabaseVersion == null){

            // No local DB
            if(isNetworkConnected){

                this.createLocalDatabase()
            }
            else{

                //Display error message asking to connect and relaunch app
                MaterialAlertDialogBuilder(context)
                    .setTitle(context.getString(R.string.warning_no_connection_title))
                    .setMessage(context.getString(R.string.warning_no_data_connection_desc))
                    .setPositiveButton(context.getString(R.string.global_shutdown)) { dialog, which ->

                        // Respond to positive button press
                        exitProcess(-1)
                    }
                    .show()
            }
        }
        else{

            if(isNetworkConnected){

                dataProvider.getDatabaseInformations { remoteDatabaseInformations ->

                    if(remoteDatabaseInformations != null){

                        if(remoteDatabaseInformations.version > localDatabaseVersion){
                            this.createLocalDatabase()
                        }
                    }
                }
            }
            else{

                //Display warning message to indicate that user is not connected and maybe it's not the latest version
                MaterialAlertDialogBuilder(context)
                    .setTitle(context.getString(R.string.warning_no_connection_title))
                    .setMessage(context.getString(R.string.warning_no_connection_new_version_desc))
                    .setPositiveButton(context.getString(R.string.global_continue)) { dialog, which ->
                        // Respond to positive button press
                        protocol?.localProfileCreated()
                    }
                    .show()
            }

        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw      = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }


    private fun createLocalDatabase() {

        dataProvider.getRemoteCompanies{ companies ->

            localDataManager.createCompanies(companies)
        }

        dataProvider.getDatabaseInformations {informations ->

            localDataManager.createInformations(informations)
        }

        dataProvider.getRemoteProfile {profile ->

            localDataManager.createProfile(profile)
            protocol?.localProfileCreated()
        }

        dataProvider.getRemoteFormations {formations ->

            localDataManager.createFormations(formations)
        }

        dataProvider.getRemoteContact {contact ->

            localDataManager.createContact(contact)
        }
    }
}