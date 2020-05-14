package fr.ascotte.cv.kotlin.data.local

import fr.ascotte.cv.kotlin.data.`object`.*
import io.realm.Realm

class LocalDataManager {

    private val realm: Realm = Realm.getDefaultInstance()


    init {

        // check version local


//        return realm.where(LocalShowFavorite::class.java).equalTo("id", show.id).count() > 0

        getLocalDatabaseVersion()
    }

    fun getLocalDatabaseVersion() : Float?{

        var informations = realm.where(RealmInformations::class.java).findFirst()
        return informations?.version
    }

    fun createClients(clients: List<Client>) {

        realm.beginTransaction()
        for(client in clients){

            var realmObj = RealmClient(client)
            realm.copyToRealm(realmObj)
        }
         realm.commitTransaction()
    }

    fun createCompanies(companies: List<Company>) {

        realm.beginTransaction()
        for(company in companies){

            var realmObj = RealmCompany(company)
            realm.copyToRealm(realmObj)
        }
        realm.commitTransaction()
    }

    fun createExperiences(experiences: List<Experience>) {

        realm.beginTransaction()
        for(experience in experiences){

            var realmObj = RealmExperience(experience)
            realm.copyToRealm(realmObj)
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