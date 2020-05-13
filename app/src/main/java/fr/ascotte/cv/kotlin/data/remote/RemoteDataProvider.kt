package fr.ascotte.cv.kotlin.data.remote

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RemoteDataProvider(context:Context) {

    //private val mDatabase = FirebaseDatabase.getInstance().getReference();

    private var clients = mutableListOf<Client>()
    private var companies = mutableListOf<Company>()
    private var experiences = mutableListOf<Experience>()

    init {

        var refClients = FirebaseDatabase.getInstance().getReference("clients")
        refClients.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (productSnapshot in dataSnapshot.children) {
                    val client = productSnapshot.getValue(Client::class.java)
                    clients.add(client!!)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                throw databaseError.toException()
            }
        })

        var refCompanies = FirebaseDatabase.getInstance().getReference("companies")
        refCompanies.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (productSnapshot in dataSnapshot.children) {
                    val company = productSnapshot.getValue(Company::class.java)
                    companies.add(company!!)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                throw databaseError.toException()
            }
        })

        var refExperiences = FirebaseDatabase.getInstance().getReference("experiences")
        refExperiences.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (productSnapshot in dataSnapshot.children) {
                    val experience = productSnapshot.getValue(Experience::class.java)
                    experiences.add(experience!!)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                throw databaseError.toException()
            }
        })
    }
}