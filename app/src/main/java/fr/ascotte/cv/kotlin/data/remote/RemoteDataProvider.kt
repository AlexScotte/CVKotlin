package fr.ascotte.cv.kotlin.data.remote

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.ascotte.cv.kotlin.data.`object`.Client
import fr.ascotte.cv.kotlin.data.`object`.Company
import fr.ascotte.cv.kotlin.data.`object`.Experience
import fr.ascotte.cv.kotlin.data.`object`.Informations

class RemoteDataProvider(context:Context) {

    private var clients = mutableListOf<Client>()
    private var companies = mutableListOf<Company>()
    private var experiences = mutableListOf<Experience>()
    private var informations: Informations? = null

    init {

    }

    fun getDatabaseInformations(resultHandler:(Informations) -> Unit){

        var ref = FirebaseDatabase.getInstance().getReference("informations")
        ref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var version = dataSnapshot.child("version").getValue(Float::class.java)
                informations = Informations(
                    version ?: 0F
                )
                resultHandler(informations!!)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                throw databaseError.toException()
            }
        })
    }

    fun getRemoteClients(resultHandler: (List<Client>) -> Unit){

        var ref = FirebaseDatabase.getInstance().getReference("clients")
        ref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (snapshot in dataSnapshot.children) {
                    val client = snapshot.getValue(Client::class.java)
                    clients.add(client!!)
                }

                resultHandler(clients)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                throw databaseError.toException()
            }
        })
    }

    fun getRemoteCompanies(resultHandler: (List<Company>) -> Unit){

        var ref = FirebaseDatabase.getInstance().getReference("companies")
        ref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (snapshot in dataSnapshot.children) {
                    val company = snapshot.getValue(Company::class.java)
                    companies.add(company!!)
                }
                resultHandler(companies)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                throw databaseError.toException()
            }
        })
    }

    fun getRemoteExperiences(resultHandler: (List<Experience>) -> Unit){

        var ref = FirebaseDatabase.getInstance().getReference("experiences")
        ref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (snapshot in dataSnapshot.children) {
                    val experience = snapshot.getValue(Experience::class.java)
                    experiences.add(experience!!)
                }
                resultHandler(experiences)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                throw databaseError.toException()
            }
        })
    }
}