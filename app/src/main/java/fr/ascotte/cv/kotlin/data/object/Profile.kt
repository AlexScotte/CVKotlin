package fr.ascotte.cv.kotlin.data.`object`

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.io.Serializable


data class Profile(
    val age:Int = 0,
    val backgroundImageUrl:String = "",
    val birthDate:String = "",
    val description:String = "",
    val firstName:String = "",
    val imageUrl:String="",
    val job:String = "",
    val lastName:String = "",
    val location:String = "")
    : Serializable{

    var hobbies:ArrayList<Hobby> = ArrayList()

    constructor(rProfile: RealmProfile):this(

        rProfile.age,
        rProfile.backgroundImageUrl,
        rProfile.birthDate,
        rProfile.description,
        rProfile.firstName,
        rProfile.imageUrl,
        rProfile.job,
        rProfile.lastName,
        rProfile.location
    ){
        for(rHobby in rProfile.rHobbies){

            val hobby = Hobby(rHobby)
            hobbies.add(hobby)
        }
    }
}

@RealmClass
open class RealmProfile(
    var age:Int = 0,
    var backgroundImageUrl:String = "",
    var birthDate:String = "",
    var description:String = "",
    var firstName:String = "",
    var imageUrl:String="",
    var job:String = "",
    var lastName:String = "",
    var location:String = "")
    : RealmObject(){

    var rHobbies: RealmList<RealmHobby> = RealmList()

    constructor(profile: Profile) : this(

        profile.age,
        profile.backgroundImageUrl,
        profile.birthDate,
        profile.description,
        profile.firstName,
        profile.imageUrl,
        profile.job,
        profile.lastName,
        profile.location
    ){
        for(hobby in profile.hobbies){

            val rHobby = RealmHobby(hobby)
            rHobbies.add(rHobby)
        }
    }
}