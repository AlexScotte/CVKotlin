package fr.ascotte.cv.kotlin.data.`object`

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable

data class Competence(
        val id:Int = 0,
        val name:String = "",
        val important: Int = 0)
    : Serializable {

    constructor(realmCompetence: RealmCompetence):this(

            realmCompetence.id,
            realmCompetence.name,
            realmCompetence.important
    )
}

@RealmClass
open class RealmCompetence(
        var id:Int = 0,
        var name:String = "",
        var important: Int = 0)
    : RealmObject() {

    constructor(competence: Competence) : this(

            competence.id,
            competence.name,
            competence.important
    )
}
