package fr.ascotte.cv.kotlin.data.`object`

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable

data class DetailsExperience(
    val context:String = "",
    val missions:String = ""
) : Serializable {

    constructor(rDetails: RealmDetailsExperience?) : this(
        rDetails?.context ?: "",
        rDetails?.missions?: ""
    )
}

@RealmClass
open class RealmDetailsExperience(
    var context:String = "",
    var missions: String=""
) : RealmObject(){

    constructor(details: DetailsExperience?) : this(
        details?.context ?: "",
        details?.missions ?: ""
    )
}