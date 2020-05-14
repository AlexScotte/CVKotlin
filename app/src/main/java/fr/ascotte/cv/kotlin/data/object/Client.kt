package fr.ascotte.cv.kotlin.data.`object`

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable


data class Client(val id:Int = 0, val name:String = "", val site:String = "") : Serializable

@RealmClass
open class RealmClient() : RealmObject() {

    @PrimaryKey var id = 0
    var name=""
    var site=""

    constructor(client: Client) : this() {

        this.id = client.id
        this.name = client.name
        this.site = client.site
    }
}