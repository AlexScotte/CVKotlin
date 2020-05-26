package fr.ascotte.cv.kotlin.data.`object`

import android.telecom.Call
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable

data class Experience(
        val id:Int = 0,
        val job:String = "",
        val duration:String = "",
        val details:DetailsExperience? = null
        ) : Serializable{

    var skills:ArrayList<Competence> = ArrayList()
    var idClient:Int = 0

    constructor(rExperience: RealmExperience) : this(
        rExperience.id,
        rExperience.job,
        rExperience.duration,
        DetailsExperience(rExperience.rDetails)
    )
    {
        for(rSkill in rExperience.rSkills){

            val skill = Competence(rSkill)
            skill.idExperience = rExperience.id
            skills.add(skill)
        }
    }
}

@RealmClass
open class RealmExperience(
        var id:Int = 0,
        var job:String = "",
        var duration: String="",
        var rDetails:RealmDetailsExperience? = null) : RealmObject(){

    var idClient:Int = 0
    var rSkills:RealmList<RealmCompetence> = RealmList()

    constructor(experience: Experience) : this(

        experience.id,
        experience.job,
        experience.duration,
        RealmDetailsExperience(experience.details)
    ){

        for(skill in experience.skills){

            val rSkill = RealmCompetence(skill)
            rSkill.idExperience = experience.id
            rSkills.add(rSkill)
        }
    }
}