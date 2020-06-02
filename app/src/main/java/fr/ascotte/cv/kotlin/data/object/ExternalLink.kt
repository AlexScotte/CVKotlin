package fr.ascotte.cv.kotlin.data.`object`

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.io.Serializable

data class ExternalLink(
    val id:Int = 0,
    val imageUrl:String = "",
    val name:String = "",
    val url:String = "")
    : Serializable {

    constructor(rExternalLink: RealmExternalLink):this(

        rExternalLink.id,
        rExternalLink.imageUrl,
        rExternalLink.name,
        rExternalLink.url
    )
}

@RealmClass
open class RealmExternalLink(
    var id:Int = 0,
    var imageUrl:String = "",
    var name:String = "",
    var url:String = "")
    : RealmObject(){

    constructor(externalLink: ExternalLink) : this(

        externalLink.id,
        externalLink.imageUrl,
        externalLink.name,
        externalLink.url
    )
}