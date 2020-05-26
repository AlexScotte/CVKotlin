package fr.ascotte.cv.kotlin.data.`object`

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable

data class Company(
        val id:Int = 0,
        val dateStart:String = "",
        val dateEnd:String ="",
        val name:String = "",
        val job:String = "",
        val department:String = "",
        val town:String = "") : Serializable{

    var clients:ArrayList<Client> = ArrayList()

    constructor(rCompany: RealmCompany): this(

            rCompany.id,
            rCompany.dateStart,
            rCompany.dateEnd,
            rCompany.name,
            rCompany.job,
            rCompany.department,
            rCompany.town
    ){
        for (rCLient in rCompany.rClients){

            val client = Client(rCLient)
            client.idCompany = rCLient.idCompany
            clients.add(client)
        }
    }
}

@RealmClass
open class RealmCompany(
        @PrimaryKey var id:Int = 0,
        var dateStart:String = "",
        var dateEnd:String ="",
        var name:String = "",
        var job:String = "",
        var department:String = "",
        var town:String = "")
    : RealmObject() {

     var rClients: RealmList<RealmClient> = RealmList()

    constructor(company: Company) : this(

            company.id,
            company.dateStart,
            company.dateEnd,
            company.name,
            company.job,
            company.department,
            company.town
    ){

        for (client in company.clients){

            val rClient = RealmClient(client)
            rClient.idCompany = company.id
            rClients.add(rClient)
        }
    }
}