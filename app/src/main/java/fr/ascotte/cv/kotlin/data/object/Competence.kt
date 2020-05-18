package fr.ascotte.cv.kotlin.data.`object`

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable

data class Competence(
        val id:Int = 0,
        val idExperience:Int = 0,
        val name:String = "")
    : Serializable {

    constructor(realmCompetence: RealmCompetence):this(

            realmCompetence.id,
            realmCompetence.idExperience,
            realmCompetence.name
    )
}

@RealmClass
open class RealmCompetence(
        @PrimaryKey var id:Int = 0,
        var idExperience:Int = 0,
        var name:String = "")
    : RealmObject(){

    constructor(competence: Competence) : this(

            competence.id,
            competence.idExperience,
            competence.name
    )
}