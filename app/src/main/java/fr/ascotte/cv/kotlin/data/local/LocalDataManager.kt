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

        var rInfo = realm.where(RealmInformations::class.java).findFirst()
        return rInfo?.version
    }

    fun getProfile() : Profile?{

        var rProfile = realm.where(RealmProfile::class.java).findFirst()
        return Profile(rProfile!!)
    }


    fun getCompanies() : List<Company>{

        var companies = realm.where(RealmCompany::class.java).findAll().map { rCompany -> Company(rCompany) }
        return companies
    }

    fun createProfile(profile: Profile) {

        realm.beginTransaction()
        var realmObj = RealmProfile(profile)
        realm.copyToRealm(realmObj)
        realm.commitTransaction()
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