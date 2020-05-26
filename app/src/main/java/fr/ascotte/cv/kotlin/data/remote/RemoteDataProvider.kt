package fr.ascotte.cv.kotlin.data.remote

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.ascotte.cv.kotlin.data.`object`.*

class RemoteDataProvider(context:Context) {

    private var companies = mutableListOf<Company>()
    private var informations: Informations? = null

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
}