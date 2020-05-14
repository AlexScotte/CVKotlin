package fr.ascotte.cv.kotlin.data.`object`

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.io.Serializable

data class Informations(val version:Float = 0f) : Serializable

@RealmClass
open class RealmInformations() : RealmObject() {

    var version:Float = 0F

    constructor(informations: Informations) : this() {

        this.version = informations.version
    }
}