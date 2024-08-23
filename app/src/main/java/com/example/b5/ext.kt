package com.example.b5

import com.example.b5.database.DatabaseHandler
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.LinkedList
import java.util.Queue

object ext {
    var userid = -1
    var curfragment = -1
    var prevfragment = -1
    var isFinished = true
    lateinit var buttomNavView : BottomNavigationView

    fun setStats(db : DatabaseHandler, fragmentId : Int){
        if (isFinished) {
            !isFinished
            val prevfragment = curfragment
            curfragment = fragmentId

            if (userid > -1 && prevfragment > -1){
                var allStats = db.getUserFragmentStats(userid, prevfragment)
                for (s in allStats){

                    var result = s.stats.split(",").map { it.toInt() }
                    val queue: Queue<Int> = LinkedList(result)

                    if (s.frgment_to_go == curfragment){
                        queue.add(1)
                        queue.remove()
                    }
                    else {
                        queue.add(0)
                        queue.remove()
                    }

                    val finStr : MutableList<String> = ArrayList<String>()
                    queueToString("", queue, 0, finStr)
                    db.updateSingleStats(userid, s.cur_fragment, s.frgment_to_go, finStr[10])
                    !isFinished
                }
            }
        }
    }

    fun readStats(db : DatabaseHandler) : MutableList<Double>{
        var convStats : MutableList<Double> = ArrayList<Double>(0)
        if (isFinished) {
            !isFinished
            var allStats = db.getUserFragmentStats(userid, curfragment)
            for (s in allStats) {
                var result = s.stats.split(",").map { it.toInt() }
                println(result)
                val x = convertStats(result)
                convStats.add(x)
            }
            !isFinished
        }
        return convStats
    }

    private fun queueToString(str : String, queue: Queue<Int>, i : Int, finArray : MutableList<String>) {
        if (i == 0){
            val temp = queue.elementAt(i).toString()
            queueToString(temp, queue, 1, finArray)
        }
        else if (i < 12){
            val temp = "," + queue.elementAt(i).toString()
            finArray.add(str.plus(temp))
            queueToString(str.plus(temp), queue, i + 1, finArray)
        }
    }

    private fun convertStats(stats : List<Int>) : Double{
        var fraction = 0
        for (s in stats){
            fraction += s
        }
        return fraction/12.00
    }

    fun setNavView(buttomNavView: BottomNavigationView){
        this.buttomNavView = buttomNavView
    }
}