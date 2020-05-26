package fr.ascotte.cv.kotlin.data.local

import fr.ascotte.cv.kotlin.data.`object`.*
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults

class LocalDataManager {

    private val realm: Realm = Realm.getDefaultInstance()

    init {

        getLocalDatabaseVersion()
    }

    fun getLocalDatabaseVersion() : Float?{

        var informations = realm.where(RealmInformations::class.java).findFirst()
        return informations?.version
    }

    fun getCompanies() : List<Company>{

        var companies = realm.where(RealmCompany::class.java).findAll().map { rCompany -> Company(rCompany) }
        return companies
    }

    fun createCompanies(companies: List<Company>) {

        realm.beginTransaction()
        for(company in companies){

            val rCompany = RealmCompany(company)
            realm.copyToRealm(rCompany)
        }
        realm.commitTransaction()
    }

    fun createInformations(informations: Informations) {

        realm.beginTransaction()
        var realmObj = RealmInformations(informations)
        realm.copyToRealm(realmObj)
        realm.commitTransaction()
    }
}