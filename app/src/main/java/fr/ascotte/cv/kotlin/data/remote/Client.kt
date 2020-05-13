package fr.ascotte.cv.kotlin.data.remote

import java.io.Serializable

class Client() : Serializable{

    var id:Int= 0
    var name:String = ""
    var site:String = ""

    constructor(id:Int, name:String, site:String) : this() {

        this.id = id
        this.name = name
        this.site = site
    }
}