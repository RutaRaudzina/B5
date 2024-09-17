package com.example.b5.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import com.example.b5.R
import com.example.b5.TransferData
import com.example.b5.database.DatabaseHandler
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
        if (ext.activateAUI) root.findViewById<Button>(R.id.activateAUIbtn).setText("Dectivate AUI")
        else root.findViewById<Button>(R.id.activateAUIbtn).setText("Activate AUI")

        if (ext.buttonsCount == 5)
            root.findViewById<Button>(R.id.buttonsCountBtn).setText("Set to 3 buttons")
        else
            root.findViewById<Button>(R.id.buttonsCountBtn).setText("Set to 5 buttons")

        if (ext.userid > -1){
            transferData = activity as TransferData
            if (ext.activateAUI) transferData.setBottomMenuButtons(ext.buttonsCount)
            if (ext.taskNr != 0) ext.addToSequence()
        }

        getTaskCompletionTime(db)

        root.findViewById<Button>(R.id.task1Btn).setOnClickListener {
            val dbResult = db.getTaskSequenceData(ext.userid, 1, ext.activateAUI)
            if (dbResult.isEmpty()) {
                ext.taskNr = 1
                ext.systemTime = System.currentTimeMillis()
                ext.sequence = "0"
                Toast.makeText(requireContext(), "Task 1 started.", Toast.LENGTH_LONG).show()
            }
            else Toast.makeText(requireContext(), "Task has already been completed.",
                Toast.LENGTH_LONG).show()
        }

        root.findViewById<Button>(R.id.task2Btn).setOnClickListener {
            val dbResult = db.getTaskSequenceData(ext.userid, 2, ext.activateAUI)
            if (dbResult.isEmpty()) {
                ext.taskNr = 2
                ext.systemTime = System.currentTimeMillis()
                ext.sequence = "0"
                Toast.makeText(requireContext(), "Task 2 started.", Toast.LENGTH_LONG).show()
            }
            else Toast.makeText(requireContext(), "Task has already been completed.",
                Toast.LENGTH_LONG).show()
        }

        root.findViewById<Button>(R.id.task3Btn).setOnClickListener {
            val dbResult = db.getTaskSequenceData(ext.userid, 3, ext.activateAUI)
            transferData.setBottomMenuButtons(ext.buttonsCount)

            if (dbResult.isEmpty()) {
                ext.taskNr = 3
                ext.systemTime = System.currentTimeMillis()
                ext.sequence = "0"
                Toast.makeText(requireContext(), "Task 3 started.", Toast.LENGTH_LONG).show()
            }
            else Toast.makeText(requireContext(), "Task has already been completed.",
                Toast.LENGTH_LONG).show()
        }

        root.findViewById<Button>(R.id.activateAUIbtn).setOnClickListener {
            ext.activateAUI = !ext.activateAUI
            if (ext.activateAUI) root.findViewById<Button>(R.id.activateAUIbtn).setText("Deactivate AUI")
            else root.findViewById<Button>(R.id.activateAUIbtn).setText("Activate AUI")
            ext.navViewVisibility()
            refreshFragment()
        }

        root.findViewById<Button>(R.id.buttonsCountBtn).setOnClickListener {
            if (ext.buttonsCount == 5) {
                ext.buttonsCount = 3
                root.findViewById<Button>(R.id.buttonsCountBtn).setText("Set to 5 buttons")
            }
            else {
                ext.buttonsCount = 5
                root.findViewById<Button>(R.id.buttonsCountBtn).setText("Set to 3 buttons")
            }
            refreshFragment()
        }

        return root
    }

    fun getTaskCompletionTime(db:DatabaseHandler){
        if (ext.userid < 0 || ext.taskNr == 0) return
        val time = System.currentTimeMillis()
        val timeDifference = time - ext.systemTime
        println("Sequence is: ${ext.sequence}")
        db.updateTaskData(ext.userid, ext.taskNr, timeDifference, ext.sequence, ext.activateAUI)
        val taskData = db.getTaskSequenceData(ext.userid, ext.taskNr, ext.activateAUI)
        println("Task data are: Userid ${taskData[0].user_id}, " +
                "taskNr ${taskData[0].task_nr}, timeDiff ${taskData[0].time_diff}, " +
                "sequence ${taskData[0].sequence}")
        Toast.makeText(requireContext(), "Task ${ext.taskNr} finished.", Toast.LENGTH_LONG).show()
        ext.taskNr = 0
        ext.systemTime = 0
//        ext.buttonsCount = 5
    }

    fun refreshFragment(){
//        val ft = parentFragmentManager.beginTransaction()
//        ft.detach(this).attach(this).commit()
        val id = findNavController().currentDestination?.id
        findNavController().popBackStack(id!!,true)
        findNavController().navigate(id)
    }
}