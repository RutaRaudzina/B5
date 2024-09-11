package com.example.b5.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.b5.R
import com.example.b5.database.DatabaseHandler
import com.example.b5.database.UserClickStats
import com.example.b5.ext
import java.util.Arrays
import java.util.LinkedList
import java.util.Queue


class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_first, container, false)

        println("\nFirst fragment")
        val db = DatabaseHandler(context)
        ext.setStats(db, 1)
        if (ext.taskNr != 0){
            ext.addToSequence()
        }

        return root
    }
}