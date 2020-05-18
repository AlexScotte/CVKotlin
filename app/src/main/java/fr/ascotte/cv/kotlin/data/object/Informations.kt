package fr.ascotte.cv.kotlin.data.`object`

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.io.Serializable

data class Informations(val version:Float = 0f) : Serializable{

    constructor(realmInformations: RealmInformations) : this(
            realmInformations.version
    )
}

@RealmClass
open class RealmInformations(var version:Float = 0f) : RealmObject() {

    constructor(informations: Informations) : this() {

        this.version = informations.version
    }
}