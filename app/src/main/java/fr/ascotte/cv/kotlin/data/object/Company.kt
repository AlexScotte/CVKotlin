package fr.ascotte.cv.kotlin.data.`object`

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable

data class Company(val id:Int = 0, val name:String = "", val department:String = "", val town:String = "") : Serializable

@RealmClass
open class RealmCompany() : RealmObject() {

    @PrimaryKey var id:Int= 0
    var name:String = ""
    var department:String = ""
    var town:String = ""

    constructor(company: Company) : this() {

        this.id = company.id
        this.department = company.department
        this.town = company.town
    }
}