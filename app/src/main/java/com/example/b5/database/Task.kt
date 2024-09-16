package com.example.b5.database

class Task {
    var user_id : Int = 0
    var task_nr : Int = 0
    var time_diff : Long = 0
    var sequence : String = ""
    var activatedAUI = false

    constructor(){}

    constructor(user_id : Int, task_nr : Int, time_diff : Long, sequence : String, activatedAUI:Boolean){
        this.user_id = user_id
        this.task_nr = task_nr
        this.time_diff = time_diff
        this.sequence = sequence
        this.activatedAUI = activatedAUI
    }
}