package fr.ascotte.cv.kotlin.data.`object`

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable


data class Client(
        val id:Int = 0,
        val name:String = "",
        val site:String = "",
        val imageUrl:String="")
    : Serializable{

    var experience:Experience? = null
    var idCompany:Int = 0

    constructor(rClient: RealmClient):this(

            rClient.id,
            rClient.name,
            rClient.site,
            rClient.imageUrl
    ){
        if(rClient.rExperience == null)
            experience = Experience()
        else
            experience = Experience(rClient.rExperience!!)

        experience?.idClient = rClient.id
    }
}

@RealmClass
open class RealmClient(
        var id:Int = 0,
        var name:String = "",
        var site:String = "",
        var imageUrl:String="")
    : RealmObject(){

    var rExperience:RealmExperience? = null
    var idCompany:Int = 0

    constructor(client: Client) : this(

            client.id,
            client.name,
            client.site,
            client.imageUrl
    ){

        if(client.experience == null)
            rExperience = RealmExperience()
        else
            rExperience = RealmExperience(client.experience!!)

        rExperience?.idClient = client.id
    }
}
