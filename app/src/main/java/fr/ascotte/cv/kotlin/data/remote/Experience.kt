package fr.ascotte.cv.kotlin.data.remote

import java.io.Serializable

class Experience() : Serializable {

    var id:Int= 0
    var idClient:Int= 0
    var job:String = ""
    var dateStart:String = ""
    var dateEnd:String = ""

    constructor(id:Int, idClient: Int, job:String, dateStart:String, dateEnd:String) : this() {

        this.id = id
        this.idClient = idClient
        this.job = job
        this.dateStart = dateStart
        this.dateEnd = dateEnd
    }
}