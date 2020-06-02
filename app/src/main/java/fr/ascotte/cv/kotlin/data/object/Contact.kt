package fr.ascotte.cv.kotlin.data.`object`

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.io.Serializable

data class Contact(
    val cvUrl:String = "",
    val email:String = "",
    val phone:String = "")
    : Serializable {

    var externalLinks:ArrayList<ExternalLink> = ArrayList()

    constructor(rContact: RealmContact):this(

        rContact.cvUrl,
        rContact.email,
        rContact.phone
    )
    {
        for(rExternalLink in rContact.rExternalLinks){

            val link = ExternalLink(rExternalLink)
            externalLinks.add(link)
        }
    }

}

@RealmClass
open class RealmContact(
    var cvUrl:String = "",
    var email:String = "",
    var phone:String = "")
    : RealmObject(){

    var rExternalLinks: RealmList<RealmExternalLink> = RealmList()

    constructor(contact: Contact) : this(

        contact.cvUrl,
        contact.email,
        contact.phone
    ){
        for(externalLink in contact.externalLinks){

            val rLink = RealmExternalLink(externalLink)
            rExternalLinks.add(rLink)
        }
    }
}