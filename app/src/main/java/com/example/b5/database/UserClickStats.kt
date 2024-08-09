package com.example.b5.database

class UserClickStats {
    var user_id : Int = 0
    var cur_fragment : Int = 0
    var frgment_to_go: Int = 0
    var stats: String = ""

    constructor(){}

    constructor(userId: Int, curFragment: Int, fragmentToGo: Int, stats: String){
        this.user_id = userId
        this.cur_fragment = curFragment
        this.frgment_to_go = fragmentToGo
        this.stats = stats
    }
}