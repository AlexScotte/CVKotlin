package fr.ascotte.cv.kotlin.data.`object`

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable


data class Client(
        val id:Int = 0,
        val idCompany:Int = 0,
        val name:String = "",
        val site:String = "")
    : Serializable{

    constructor(realmClient: RealmClient):this(

            realmClient.id,
            realmClient.idCompany,
            realmClient.name,
            realmClient.site
    )
}

@RealmClass
open class RealmClient(
        @PrimaryKey var id:Int = 0,
        var idCompany:Int = 0,
        var name:String = "",
        var site:String = "")
    : RealmObject(){

    constructor(client: Client) : this(

        client.id,
        client.idCompany,
        client.name,
        client.site
    )
}
