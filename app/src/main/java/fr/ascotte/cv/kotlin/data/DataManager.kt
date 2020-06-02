package fr.ascotte.cv.kotlin.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import fr.ascotte.cv.kotlin.data.local.LocalDataManager
import fr.ascotte.cv.kotlin.data.remote.RemoteDataProvider


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

                TODO("Display error message to connect and relaunch app")
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

                TODO("Display warning message to indicate that not connected and maybe it's not the latest version")
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
    }
}