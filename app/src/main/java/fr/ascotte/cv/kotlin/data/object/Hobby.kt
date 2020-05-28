package fr.ascotte.cv.kotlin.data.`object`

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.io.Serializable

data class Hobby(
    val id:Int = 0,
    val name:String = "")
    : Serializable {

    constructor(rHobbies: RealmHobby):this(

        rHobbies.id,
        rHobbies.name
    )
}

@RealmClass
open class RealmHobby(
    var id:Int = 0,
    var name:String = "")
    : RealmObject(){

    constructor(hobby: Hobby) : this(

        hobby.id,
        hobby.name
    )
}