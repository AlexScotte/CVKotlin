package fr.ascotte.cv.kotlin.data.remote

import java.io.Serializable

class Company() : Serializable {

    var id:Int= 0
    var name:String = ""
    var department:String = ""
    var town:String = ""

    constructor(id:Int, name:String, department:String, town:String) : this() {

        this.id = id
        this.name = name
        this.department = department
        this.town = town
    }
}