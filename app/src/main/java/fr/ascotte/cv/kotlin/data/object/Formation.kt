package fr.ascotte.cv.kotlin.data.`object`

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.io.Serializable

data class Formation(
    val id:Int = 0,
    val name:String = "",
    val date:String = "",
    val establishment:String = "",
    val town:String = "",
    val description:String = "",
    val url:String = "")
    : Serializable {

    constructor(rFormation: RealmFormation):this(

        rFormation.id,
        rFormation.name,
        rFormation.date,
        rFormation.establishment,
        rFormation.town,
        rFormation.description,
        rFormation.url
    )
}

@RealmClass
open class RealmFormation(
    var id:Int = 0,
    var name:String = "",
    var date:String = "",
    var establishment:String = "",
    var town:String = "",
    var description:String = "",
    var url:String = "")
    : RealmObject(){

    constructor(formation: Formation) : this(

        formation.id,
        formation.name,
        formation.date,
        formation.establishment,
        formation.town,
        formation.description,
        formation.url
    )
}