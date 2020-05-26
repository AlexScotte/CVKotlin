package fr.ascotte.cv.kotlin.data.`object`

import android.telecom.Call
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
        val details:DetailsExperience? = null
        ) : Serializable{

    var skills:ArrayList<Competence> = ArrayList()

    constructor(realmExperience: RealmExperience) : this(
            realmExperience.id,
            realmExperience.idClient,
            realmExperience.job,
            realmExperience.duration,
            DetailsExperience(realmExperience.details)
    )
}

@RealmClass
open class RealmExperience(
        @PrimaryKey var id:Int = 0,
        var idClient: Int = 0,
        var job:String = "",
        var duration: String="",
        var details:RealmDetailsExperience? = null) : RealmObject(){

    var skills:RealmList<RealmCompetence> = RealmList()

    constructor(experience: Experience) : this(

        experience.id,
        experience.idClient,
        experience.job,
        experience.duration,
        RealmDetailsExperience(experience.details)
    )
}