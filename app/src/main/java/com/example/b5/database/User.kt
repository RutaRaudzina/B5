package com.example.b5.database

class User {
    var user_id : Int = 0
    var username : String = ""
    var email : String = ""
    var password : String = ""
    var avatar : String = ""

    constructor(){}

    constructor(username:String, email:String, password:String, avatar:String){
        this.username = username
        this.email = email
        this.password = password
        this.avatar = avatar
    }

}