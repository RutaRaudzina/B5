package com.example.b5.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
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

        if (ext.userid > -1){
            transferData = activity as TransferData
        }

        root.findViewById<Button>(R.id.task1Btn).setOnClickListener {

        }

        root.findViewById<Button>(R.id.task2Btn).setOnClickListener {

        }

        return root
    }
}