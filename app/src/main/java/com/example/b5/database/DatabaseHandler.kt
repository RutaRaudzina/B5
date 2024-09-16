package com.example.b5.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_NAME = "adaptive_ui"
const val TABLE_NAME = "users"
const val COL_USER_ID = "user_id"
const val COL_USERNAME = "username"
val COL_EMAIL = "email"
val COL_PASSWORD = "password"
val COL_AVATAR = "avatar"

val TABLE_NAME_1 = "user_click_stats"
val COL_CUR_FRAGMENT = "cur_fragment"
val COL_FRAGMENT_TO_GO = "fragment_to_go"
val COL_STATS = "stats"

val TABLE_NAME_2 = "tasks"
val COL_TASK_NR = "task_nr"
val COL_TIME_DIFF = "time_diff"
val COL_SEQUENCE = "sequence"
val COL_AUI_ACTIVATED = "AUI_activated"

class DatabaseHandler(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME,null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " VARCHAR(256), " +
                COL_EMAIL + " VARCHAR(255), " +
                COL_PASSWORD + " VARCHAR(255), " +
                COL_AVATAR + " VARCHAR(255)); "

        val createTable2 = "CREATE TABLE $TABLE_NAME_1 (" +
                "$COL_USER_ID INTEGER, " +
                "$COL_CUR_FRAGMENT INTEGER, " +
                "$COL_FRAGMENT_TO_GO INTEGER, " +
                "$COL_STATS VARCHAR(255));"

        val createTable3 = "CREATE TABLE $TABLE_NAME_2 (" +
                "$COL_USER_ID INTEGER, " +
                "$COL_TASK_NR INTEGER, " +
                "$COL_TIME_DIFF BIGINT, " +
                "$COL_SEQUENCE VARCHAR(255), " +
                "$COL_AUI_ACTIVATED BOOLEAN);"


        p0!!.execSQL(createTable)
        p0!!.execSQL(createTable2)
        p0!!.execSQL(createTable3)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    fun insertUser (user: User){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_USERNAME, user.username)
        cv.put(COL_EMAIL, user.email)
        cv.put(COL_PASSWORD, user.password)
        cv.put(COL_AVATAR, user.avatar)

        var result = db.insert(TABLE_NAME, null, cv)
        if (result == -1.toLong()) println("Insertion failed")
        else {
            println("User created")
            val query = "SELECT USER_ID FROM $TABLE_NAME WHERE ROWID=$result"
            var userId = 0
            val temp = db.rawQuery(query, null)
            if (temp.moveToFirst()){
                do {
                    userId = temp.getString(0).toInt()
                } while (temp.moveToNext())
                println("User id is: $userId")

                for (i in 0 .. 11){
                    for (j in 0 .. 11){
                        var stats : String
                        if ((i == 0 && j == 9) || (i == 9 && j == 8) || (i == 9 && j == 6)
                            || (i == 8 && j == 4) || (i == 4 && j == 1) || (i == 4 && j == 11)
                            || (i == 1 && j == 9) || (i == 10 && j == 0) || (i == 6 && j == 5)
                            || (i == 5 && j == 4) || (i == 11 && j == 10))
                            stats = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1"
                        else if ((i == 9 && j == 3) || (i == 0 && j == 4) || (i == 0 && j == 8)
                            || (i == 8 && j == 1) || (i == 5 && j == 7) || (i == 5 && j == 11)
                            || (i == 4 && j == 7) || (i == 1 && j == 4) || (i == 1 && j == 3)
                            || (i == 10 && j == 2) || (i == 10 && j == 11) || (i == 6 && j == 4)
                            || (i == 6 && j == 8) || (i == 5 && j == 9) || (i == 5 && j == 10)
                            || (i == 11 && j == 5) || (i == 11 && j == 6) || (i == 8 && j == 11))
                            stats = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1"
                        else if ((i == 9 && j == 11) || (i == 9 && j == 5) || (i == 0 && j == 5)
                            || (i == 0 && j == 7) || (i == 8 && j == 7) || (i == 8 && j == 6)
                            || (i == 5 && j == 2) || (i == 5 && j == 3) || (i == 4 && j == 10)
                            || (i == 4 && j == 6) || (i == 1 && j == 7) || (i == 1 && j == 5)
                            || (i == 10 && j == 3) || (i == 10 && j == 7) || (i == 6 && j == 7)
                            || (i == 6 && j == 2) || (i == 5 && j == 6) || (i == 5 && j == 8)
                            || (i == 11 && j == 3) || (i == 11 && j == 1))
                            stats = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1"
                        else stats = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0"
                        val userStats = UserClickStats(userId, i, j, stats)

                        //Normal initial db data
//                        val userStats = UserClickStats(userId, i, j, "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0")

                        var cv2 = ContentValues()
                        cv2.put(COL_USER_ID, userStats.user_id)
                        cv2.put(COL_CUR_FRAGMENT, userStats.cur_fragment)
                        cv2.put(COL_FRAGMENT_TO_GO, userStats.frgment_to_go)
                        cv2.put(COL_STATS, userStats.stats)
                        var result2 = db.insert(TABLE_NAME_1, null, cv2)
                        if (result2 == -1.toLong()) println("Stats insertion failed")
                    }
                }
            }
            println("User created")
        }
    }

    fun readData(): MutableList<User>{
        var list : MutableList<User> = ArrayList()
        val db = readableDatabase
        val query = "SELECT * FROM " + TABLE_NAME
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do {
                var user = User()
                user.user_id = result.getString(0).toInt()
                user.username = result.getString(1)
                user.email = result.getString(2)
                user.password = result.getString(3)
                user.avatar = result.getString(4)
                list.add(user)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

    fun findUser(email: String, password: String): Int{
        val db = readableDatabase
        val query = "SELECT $COL_USER_ID FROM $TABLE_NAME WHERE $COL_EMAIL = \'$email' AND $COL_PASSWORD = \'$password'"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do {
                return result.getString(0).toInt()
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return -1
    }

    fun updateUser(userId: Int, username: String){
        val db = writableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do {
                if (result.getString(0).toInt() == userId){
                    var cv = ContentValues()
                    cv.put(COL_USERNAME, username)
                    db.update(TABLE_NAME, cv, "$COL_USER_ID=?",
                        arrayOf(result.getString(0)))
                }

            } while (result.moveToNext())
        }
        result.close()
        db.close()
    }

    fun updateSingleStats(userId: Int, prevFragment: Int, fragmentArrived : Int, newStats: String){
        val db = writableDatabase
        val query = "SELECT * FROM $TABLE_NAME_1 WHERE $COL_USER_ID=$userId AND $COL_CUR_FRAGMENT=$prevFragment" +
                " AND $COL_FRAGMENT_TO_GO=$fragmentArrived;"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(COL_USER_ID, userId)
                cv.put(COL_CUR_FRAGMENT, prevFragment)
                cv.put(COL_FRAGMENT_TO_GO, fragmentArrived)
                cv.put(COL_STATS, newStats)
                db.update(TABLE_NAME_1, cv, "$COL_USER_ID=? AND $COL_CUR_FRAGMENT=? AND $COL_FRAGMENT_TO_GO=?",
                    arrayOf(userId.toString(), prevFragment.toString(), fragmentArrived.toString())
                )
//                }
            } while (result.moveToNext())
        }
        result.close()
        db.close()
    }


    fun deleteUser(userId: Int){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COL_USER_ID=?", arrayOf(userId.toString()))
        db.delete(TABLE_NAME_1, "$COL_USER_ID=?", arrayOf(userId.toString()))
        db.close()
    }

    fun getSingleUser(userId:Int) : UserWithStats{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME t, $TABLE_NAME_1 t1" +
                " WHERE t.$COL_USER_ID = $userId AND t.$COL_USER_ID = t1.$COL_USER_ID;"
        val result = db.rawQuery(query, null)
        var userWithStats = UserWithStats()
        if (result.moveToFirst()){
            var stats : MutableList<UserClickStats> = ArrayList()
            userWithStats.stats = stats
                do {
                    var stat = UserClickStats()
                    userWithStats.userId = result.getString(0).toInt()
                    userWithStats.username = result.getString(1)
                    userWithStats.email = result.getString(2)
                    userWithStats.password = result.getString(3)
                    userWithStats.avatar = result.getString(4)

                    stat.user_id = result.getString(5).toInt()
                    stat.cur_fragment = result.getString(6).toInt()
                    stat.stats = result.getString(7)
                    stats.add(stat)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return userWithStats
    }

    fun getUserFragmentStats(userId:Int, prevFragment: Int) : MutableList<UserClickStats>{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME_1 t WHERE t.$COL_USER_ID = $userId" +
                " AND t.$COL_CUR_FRAGMENT = $prevFragment;"
        val result = db.rawQuery(query, null)
        var stats : MutableList<UserClickStats> = ArrayList()
        if (result.moveToFirst()){
            do {
                var stat = UserClickStats()
                stat.user_id = result.getString(0).toInt()
                stat.cur_fragment = result.getString(1).toInt()
                stat.frgment_to_go = result.getString(2).toInt()
                stat.stats = result.getString(3)
                stats.add(stat)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return stats
    }

    fun getSingleStats(userId: Int, prevFragment: Int, fragmentArrived : Int): MutableList<UserClickStats>{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME_1 t WHERE t.$COL_USER_ID = $userId" +
                " AND t.$COL_CUR_FRAGMENT = $prevFragment AND $COL_FRAGMENT_TO_GO = $fragmentArrived;"
        val result = db.rawQuery(query, null)
        var stats : MutableList<UserClickStats> = ArrayList()
        if (result.moveToFirst()){
            do {
                var stat = UserClickStats()
                stat.user_id = result.getString(0).toInt()
                stat.cur_fragment = result.getString(1).toInt()
                stat.frgment_to_go = result.getString(2).toInt()
                stat.stats = result.getString(3)
                stats.add(stat)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return stats
    }

    fun getTaskSequenceData(userId:Int, taskId:Int, activatedAUI:Boolean) : MutableList<Task>{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME_2 t WHERE t.$COL_USER_ID = $userId" +
                " AND t.$COL_TASK_NR = $taskId AND t.$COL_AUI_ACTIVATED = $activatedAUI;"
        val result = db.rawQuery(query, null)
        var taskData : MutableList<Task> = ArrayList()
        if (result.moveToFirst()){
            do {
                var task = Task()
                task.user_id = result.getString(0).toInt()
                task.task_nr = result.getString(1).toInt()
                task.time_diff = result.getString(2).toLong()
                task.sequence = result.getString(3)
                task.activatedAUI = result.getString(4).toBoolean()
                taskData.add(task)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return taskData
    }

    fun updateTaskData(userId: Int, taskId: Int, timeDiff : Long, sequence : String, activatedAUI:Boolean){
        val db = this.writableDatabase
        val task = Task(userId, taskId, timeDiff, sequence, activatedAUI)
        var cv = ContentValues()
        cv.put(COL_USER_ID, task.user_id)
        cv.put(COL_TASK_NR, task.task_nr)
        cv.put(COL_TIME_DIFF, task.time_diff)
        cv.put(COL_SEQUENCE, task.sequence)
        cv.put(COL_AUI_ACTIVATED, task.activatedAUI)
        var result3 = db.insert(TABLE_NAME_2, null, cv)
        if (result3 == -1.toLong()) println("Task data insertion failed")
        db.close()
    }
}