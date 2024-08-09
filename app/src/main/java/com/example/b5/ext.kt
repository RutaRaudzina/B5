package com.example.b5

import com.example.b5.database.DatabaseHandler
import java.util.LinkedList
import java.util.Queue

object ext {
    var userid = -1
    var curfragment = -1
    var prevfragment = -1
    var isFinished = true

    fun setStats(db : DatabaseHandler, fragmentId : Int){
        if (isFinished) {
            !isFinished
            val prevfragment = curfragment
            curfragment = fragmentId

            if (userid > -1 && prevfragment > -1){
                var allStats = db.getUserFragmentStats(userid, prevfragment)
                for (s in allStats){
                    println("Coming from fragment ${s.cur_fragment} " +
                            "going to fragment ${s.frgment_to_go}")
                    println("Stats are ${s.stats}")
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
                    println("Stats after change are ${finStr[10]}")
                    db.updateSingleStats(userid, s.cur_fragment, s.frgment_to_go, finStr[10])
                    !isFinished
                }
            }
        }
    }

    fun queueToString(str : String, queue: Queue<Int>, i : Int, finArray : MutableList<String>) {
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
}