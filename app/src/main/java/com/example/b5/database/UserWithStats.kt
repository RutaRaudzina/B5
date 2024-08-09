package com.example.b5.database

class UserWithStats {
    var userId : Int = 0
    var username : String = ""
    var email : String = ""
    var password : String = ""
    var avatar : String = ""
    lateinit var stats : MutableList<UserClickStats>

    constructor()
    constructor(userId: Int, username: String, email: String, password: String,
                avatar: String, stats: MutableList<UserClickStats>){
        this.userId = userId
        this.username = username
        this.email = email
        this.password = password
        this.avatar = avatar
        this.stats = stats
    }
}