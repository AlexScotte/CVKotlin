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
        var profile = if(rProfile == null) Profile() else Profile(rProfile)
        return profile
    }

    fun getCompetences() : List<Competence>?{

        val rSkills = realm.where(RealmCompetence::class.java).findAll().toList()
        val rSkillsImportant = rSkills.filter { it.important == 1 }.distinctBy { x -> x.name }
        val  skills = rSkillsImportant.map{rSkill -> Competence(rSkill)}
        return skills
    }

    fun getCompanies() : List<Company>{

        var companies = realm.where(RealmCompany::class.java).findAll().map { rCompany -> Company(rCompany) }
        return companies
    }

    fun getFormations() : List<Formation>{

        var formations = realm.where(RealmFormation::class.java).findAll().map { rFormation -> Formation(rFormation) }
        return formations
    }

    fun getContact() : Contact?{

        var rContact = realm.where(RealmContact::class.java).findFirst()
        var contact = if(rContact == null) Contact() else Contact(rContact)
        return contact
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

    fun createFormations(formations: List<Formation>) {

        realm.beginTransaction()
        for(formation in formations){

            val rFormation = RealmFormation(formation)
            realm.copyToRealm(rFormation)
        }
        realm.commitTransaction()
    }

    fun createInformations(informations: Informations) {

        realm.beginTransaction()
        var realmObj = RealmInformations(informations)
        realm.copyToRealm(realmObj)
        realm.commitTransaction()
    }

    fun createContact(contact: Contact) {

        realm.beginTransaction()
        var realmObj = RealmContact(contact)
        realm.copyToRealm(realmObj)
        realm.commitTransaction()
    }
}