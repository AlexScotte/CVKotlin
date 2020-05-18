package fr.ascotte.cv.kotlin.data.`object`

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable

data class Experience(val id:Int = 0, val idClient: Int = 0, val job:String = "", val dateStart:String = "", val dateEnd:String = "") : Serializable{

    constructor(realmExperience: RealmExperience) : this(
            realmExperience.id,
            realmExperience.idClient,
            realmExperience.job,
            realmExperience.dateStart,
            realmExperience.dateEnd
    )
}

@RealmClass
open class RealmExperience(@PrimaryKey var id:Int = 0, var idClient: Int = 0, var job:String = "", var dateStart:String = "", var dateEnd:String = "") : RealmObject(){

    constructor(experience: Experience) : this() {

        this.id = experience.id
        this.idClient = experience.idClient
        this.job = experience.job
        this.dateStart = experience.dateStart
        this.dateEnd = experience.dateEnd
    }
}