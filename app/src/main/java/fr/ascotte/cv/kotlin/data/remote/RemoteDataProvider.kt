package fr.ascotte.cv.kotlin.data.remote

import android.content.Context
import com.android.volley.Request
import fr.ascotte.cv.kotlin.data.`object`.*
import org.json.JSONObject
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.lang.StringBuilder
import java.net.URLEncoder
import com.google.gson.Gson
import fr.ascotte.cv.kotlin.extensions.fromJson
import org.json.JSONArray
import java.lang.Exception

class RemoteDataProvider(context:Context) {

    private val requestQueue = Volley.newRequestQueue(context)

    fun getDatabaseInformations(resultHandler:(Informations) -> Unit){

        var url = ApiUrls.Informations.buildUrl();
        val request = JsonObjectRequest(url, null,
            Response.Listener{ result->
                var informations : Informations = Gson().fromJson(result.toString())
                resultHandler(informations)
            }, Response.ErrorListener { error ->

                throw Exception(error.localizedMessage);
            })

        requestQueue.add((request))
    }


    fun getRemoteProfile(resultHandler: (Profile) -> Unit){

        var url = ApiUrls.Profile.buildUrl();
        val request = JsonObjectRequest(url, null,
            Response.Listener{ result->
                var profile : Profile = Gson().fromJson(result.toString())



                resultHandler(profile)
            }, Response.ErrorListener { error ->

                throw Exception(error.localizedMessage);
            })

        requestQueue.add((request))
    }

    fun getRemoteCompanies(resultHandler: (List<Company>) -> Unit){

        var url = ApiUrls.Companies.buildUrl();
        val request =  JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONArray>{ result->

                var companies = mutableListOf<Company>()
                for (i in 0 until result.length()){

                    var company : Company = Gson().fromJson(result[i].toString())
                    if(company != null)
                        companies.add(company);
                }

               resultHandler(companies)
            }, Response.ErrorListener { error ->

                throw Exception(error.localizedMessage);
            })

        requestQueue.add((request))
    }



    fun getRemoteFormations(resultHandler: (List<Formation>) -> Unit){

        var url = ApiUrls.Formations.buildUrl();
        val request =  JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONArray>{ result->

                var formations = mutableListOf<Formation>()
                for (i in 0 until result.length()){

                    var formation : Formation = Gson().fromJson(result[i].toString())
                    if(formation != null)
                        formations.add(formation);
                }

                resultHandler(formations)
            }, Response.ErrorListener { error ->

                throw Exception(error.localizedMessage);
            })

        requestQueue.add((request))
    }

    fun getRemoteContact(resultHandler: (Contact) -> Unit){

        var url = ApiUrls.Contact.buildUrl();
        val request = JsonObjectRequest(url, null,
            Response.Listener{ result->
                var contact : Contact = Gson().fromJson(result.toString())
                resultHandler(contact)
            }, Response.ErrorListener { error ->

                throw Exception(error.localizedMessage);
            })

        requestQueue.add((request))
    }
}

enum class ApiUrls(val path:String) {

    Profile("/profile"),
    Companies("/companies"),
    Formations("/formations"),
    Contact("/contact"),
    Informations("/informations"),
    ;

    private val queryParam = mutableMapOf<String, String>()
    private val apiBaseUrl = "https://ascottecv.azurewebsites.net"

    fun buildUrl(): String {

        val builder = StringBuilder(apiBaseUrl)
        builder.append(path)

        for (entry in queryParam){

            if (builder.contains("?")){
                builder.append("&")
            }
            else{
                builder.append("?")
            }
            builder.append(entry.key + "=" + URLEncoder.encode(entry.value, "UTF-8"))
        }

        return builder.toString()
    }

    fun addUrlParameter(key:String, value:String) : ApiUrls{

        queryParam[key] = value
        return this
    }
}