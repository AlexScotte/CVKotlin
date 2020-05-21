package fr.ascotte.cv.kotlin.data.`object`

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable

data class Experience(
        val id:Int = 0,
        val idClient: Int = 0,
        val job:String = "",
        val duration:String = "",
        var skills:ArrayList<Competence> = ArrayList()
        ) : Serializable{

    constructor(realmExperience: RealmExperience) : this(
            realmExperience.id,
            realmExperience.idClient,
            realmExperience.job,
            realmExperience.duration
    )
}

@RealmClass
open class RealmExperience(
        @PrimaryKey var id:Int = 0,
        var idClient: Int = 0,
        var job:String = "",
        var duration: String="",
        var skills:RealmList<RealmCompetence> = RealmList()) : RealmObject(){

    constructor(experience: Experience) : this(

        experience.id,
        experience.idClient,
        experience.job,
        experience.duration
    )
}