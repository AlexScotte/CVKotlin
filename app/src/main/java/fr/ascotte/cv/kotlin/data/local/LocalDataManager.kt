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

    fun getClients() : List<Client>{

        val realmObj = realm.where(RealmClient::class.java).findAll().map {
            realmClient -> Client(realmClient)
        }

        if(realmObj == null)
            return listOf()
        else
            return realmObj
    }

    fun getCompanies() : List<Company>{

        val realmObj = realm.where(RealmCompany::class.java).findAll().map {
            realmCompany -> Company(realmCompany)
        }

        if(realmObj == null)
            return listOf()
        else
            return realmObj
    }

    fun getExperiences() : List<Experience>{

        val realmObjects = realm.where(RealmExperience::class.java).findAll()

        var experiences:MutableList<Experience> = mutableListOf()
        for (realmObj in realmObjects){

            val xp = Experience(realmObj)
            for(skill in realmObj.skills.toList()){

                val comp = Competence(skill)
                xp.skills.add(comp)
            }
            experiences.add(xp)
        }

        return experiences
    }

    fun createClients(clients: List<Client>) {

        realm.beginTransaction()
        for(client in clients){

            var realmObj = RealmClient(client)
            realm.copyToRealm(realmObj)
        }
         realm.commitTransaction()
        this.getClients()
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

            var realmList:RealmList<RealmCompetence> = RealmList()
            for (skill in experience.skills){

                var realmObject = RealmCompetence(skill)
                realmList.add(realmObject)
            }
            realmObj.skills = realmList

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