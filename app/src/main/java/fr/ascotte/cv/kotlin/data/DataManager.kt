package fr.ascotte.cv.kotlin.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import fr.ascotte.cv.kotlin.data.local.LocalDataManager
import fr.ascotte.cv.kotlin.data.remote.RemoteDataProvider

class DataManager(context: Context) {

    val localDataManager:LocalDataManager = LocalDataManager()
    val dataProvider:RemoteDataProvider = RemoteDataProvider(context)

    init {

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

        dataProvider.getRemoteClients {clients ->

            localDataManager.createClients(clients)
        }

        dataProvider.getRemoteCompanies{ companies ->

            localDataManager.createCompanies(companies)
        }

        dataProvider.getRemoteExperiences {experiences ->

            localDataManager.createExperiences(experiences)
        }

        dataProvider.getRemoteCompetences {competences ->

            localDataManager.createCompetences(competences)
        }

        dataProvider.getDatabaseInformations {informations ->

            localDataManager.createInformations(informations)
        }

    }
}