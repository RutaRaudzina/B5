package com.example.b5.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.b5.R
import com.example.b5.TransferData
import com.example.b5.database.DatabaseHandler
import com.example.b5.databinding.FragmentHomeBinding
import com.example.b5.ext


class HomeFragment : Fragment() {
    private lateinit var transferData: TransferData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        println("\nHome fragment")
        val db = DatabaseHandler(context)

        ext.setStats(db, 0)
        val stats = ext.readStats(db)
        println(stats)

        if (ext.userid > -1){
            transferData = activity as TransferData
            transferData.setBottomMenuButtons()
            if (ext.taskNr != 0){
                ext.addToSequence()
            }
        }

        getTaskCompletionTime(db)

        root.findViewById<Button>(R.id.task1Btn).setOnClickListener {
            ext.taskNr = 1
            ext.systemTime = System.currentTimeMillis()
            ext.sequence = "0"
        }

        root.findViewById<Button>(R.id.task2Btn).setOnClickListener {
            ext.taskNr = 2
            ext.systemTime = System.currentTimeMillis()
            ext.sequence = "0"
        }

        return root
    }

    fun getTaskCompletionTime(db:DatabaseHandler){
        if (ext.userid < 0 || ext.taskNr == 0) return
        val time = System.currentTimeMillis()
        val timeDifference = time - ext.systemTime
        println("Sequence is: ${ext.sequence}")
        db.updateTaskData(ext.userid, ext.taskNr, timeDifference, ext.sequence)
        val taskData = db.getTaskSequenceData(ext.userid, ext.taskNr)
        println("Task data are: Userid ${taskData[0].user_id}, " +
                "taskNr ${taskData[0].task_nr}, timeDiff ${taskData[0].time_diff}, " +
                "sequence ${taskData[0].sequence}")
        ext.taskNr = 0
        ext.systemTime = 0
    }
}