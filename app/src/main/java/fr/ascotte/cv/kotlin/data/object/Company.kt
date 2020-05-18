package fr.ascotte.cv.kotlin.data.`object`

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable

data class Company(
        val id:Int = 0,
        val dateStart:String = "",
        val dateEnd:String ="",
        val name:String = "",
        val job:String = "",
        val department:String = "",
        val town:String = "") : Serializable{

    var clients:List<Client> = listOf()

    constructor(realmCompany: RealmCompany): this(

            realmCompany.id,
            realmCompany.dateStart,
            realmCompany.dateEnd,
            realmCompany.name,
            realmCompany.job,
            realmCompany.department,
            realmCompany.town
    )
}

@RealmClass
open class RealmCompany(
        @PrimaryKey var id:Int = 0,
        var dateStart:String = "",
        var dateEnd:String ="",
        var name:String = "",
        var job:String = "",
        var department:String = "",
        var town:String = "")
    : RealmObject() {

    constructor(company: Company) : this(

            company.id,
            company.dateStart,
            company.dateEnd,
            company.name,
            company.job,
            company.department,
            company.town
    )
}